/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberLedgerProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.OpenConsultingReservationProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.OpenWorkoutReservationProjection
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * A query [ReservationUseCase] which handles the query requests
 */
class QueryReservationUseCase(
    private val agendaId: UUID,
    private val ledgerId: UUID,
    override val eventStore: EventStore
) : ReservationUseCase() {

    /**
     * Convert a list of [ConsultingReservation] to a list of [ConsultingReservationDateFacade]
     */
    private fun convertToConsultingDateFacades(reservations: List<ConsultingReservation>):
        List<ConsultingReservationDateFacade> {
            return reservations.map {
                consultingReservation ->
                ConsultingReservationDateFacade(consultingReservation.id, consultingReservation.date)
            }
        }

    /**
     * Convert a list of [WorkoutReservation] to a list of [WorkoutReservationDateFacade]
     */
    private fun convertToWorkoutDateFacades(reservations: List<WorkoutReservation>):
        List<WorkoutReservationDateFacade> {
            return reservations.map {
                workoutReservation ->
                WorkoutReservationDateFacade(workoutReservation.id, workoutReservation.date)
            }
        }

    /**
     * Return the list of [ConsultingReservationDateFacade] stored into the agenda
     */
    fun retrieveAgendaConsultingReservation(): List<ConsultingReservationDateFacade> {
        val agenda = computeAggregate(agendaId, AgendaProjection(agendaId))
        return convertToConsultingDateFacades(agenda.retrieveConsultingReservation())
    }

    /**
     * Return the list of [WorkoutReservationDateFacade] stored into the agenda
     */
    fun retrieveAgendaWorkoutReservation(): List<WorkoutReservationDateFacade> {
        val agenda = computeAggregate(agendaId, AgendaProjection(agendaId))
        return convertToWorkoutDateFacades(agenda.retrieveWorkoutReservation())
    }

    /**
     * Return the [ConsultingReservationFacade] given its [reservationId]
     */
    fun retrieveConsultingReservation(reservationId: UUID): ConsultingReservationFacade {
        val agenda = computeAggregate(agendaId, AgendaProjection(agendaId))
        when (
            val consultingReservation = agenda.retrieveConsultingReservation()
                .firstOrNull { consultingReservation -> reservationId == consultingReservation.id }
        ) {
            is OpenConsultingReservation -> {
                val openConsultingReservation = computeAggregate(
                    consultingReservation.id,
                    OpenConsultingReservationProjection(consultingReservation)
                )
                return ConsultingReservationFacade(openConsultingReservation)
            }
            is CloseConsultingReservation -> {
                return ConsultingReservationFacade(consultingReservation)
            }
            else -> {
                throw RequestFailedException(RequestFailedMessages.reservationNotFound)
            }
        }
    }

    /**
     * Return the [WorkoutReservationFacade] given its [reservationId]
     */
    fun retrieveWorkoutReservation(reservationId: UUID): WorkoutReservationFacade {
        val agenda = computeAggregate(agendaId, AgendaProjection(agendaId))
        when (
            val workoutReservation = agenda.retrieveWorkoutReservation()
                .firstOrNull { workoutReservation -> reservationId == workoutReservation.id }
        ) {
            is OpenWorkoutReservation -> {
                val openWorkoutReservation = computeAggregate(
                    workoutReservation.id,
                    OpenWorkoutReservationProjection(workoutReservation)
                )
                return WorkoutReservationFacade(openWorkoutReservation)
            }
            is CloseWorkoutReservation -> {
                return WorkoutReservationFacade(workoutReservation)
            }
            else -> {
                throw RequestFailedException(RequestFailedMessages.reservationNotFound)
            }
        }
    }

    /**
     * Return the list of [ConsultingReservationDateFacade] stored into the member
     */
    fun retrieveMemberConsultingReservations(memberId: UUID): List<ConsultingReservationDateFacade> {
        val ledger = computeAggregate(ledgerId, MemberLedgerProjection(ledgerId))
        when (val member = ledger.retrieveAllMembers().firstOrNull { member -> member.id == memberId }) {
            is Member ->
                return convertToConsultingDateFacades(
                    computeAggregate(memberId, MemberProjection(member)).retrieveConsultingReservation()
                )
            else -> {
                throw RequestFailedException(RequestFailedMessages.reservationNotFound)
            }
        }
    }

    /**
     * Return the list of [WorkoutReservationDateFacade] stored into the member
     */
    fun retrieveMemberWorkoutReservations(memberId: UUID): List<WorkoutReservationDateFacade> {
        val ledger = computeAggregate(ledgerId, MemberLedgerProjection(ledgerId))
        when (val member = ledger.retrieveAllMembers().firstOrNull { member -> member.id == memberId }) {
            is Member ->
                return convertToWorkoutDateFacades(
                    computeAggregate(memberId, MemberProjection(member)).retrieveWorkoutReservation()
                )
            else -> {
                throw RequestFailedException(RequestFailedMessages.reservationNotFound)
            }
        }
    }
}
