/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.agenda

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import java.util.UUID

/**
 * An aggregate root that is about referencing all the reservations
 * present into the system.
 *
 * To do that, it uses two entities that help to group reservations,
 * one to group workout reservations and another to group workout reservations.
 */
class Agenda(
    private val agendaWorkoutReservation: AgendaWorkoutReservation,
    private val agendaConsultingReservation: AgendaConsultingReservation,
    val id: UUID
) {

    constructor(id: UUID) : this(AgendaWorkoutReservation(), AgendaConsultingReservation(), id)

    /**
     * Returns a new [Agenda] adding a [reservation] into the list of [ConsultingReservation]
     */
    fun addConsultingReservation(reservation: ConsultingReservation): Agenda {
        return Agenda(
            agendaWorkoutReservation,
            agendaConsultingReservation.addConsultingReservation(reservation),
            id
        )
    }

    /**
     * Returns a new [Agenda] adding a [reservation] into the list of [WorkoutReservation]
     */
    fun addWorkoutReservation(reservation: WorkoutReservation): Agenda {
        return Agenda(
            agendaWorkoutReservation.addWorkoutReservation(reservation),
            agendaConsultingReservation,
            id
        )
    }

    /**
     * Returns a new [Agenda] deleting a [reservation] from the list of [ConsultingReservation]
     */
    fun deleteConsultingReservation(reservation: ConsultingReservation): Agenda {
        return Agenda(
            agendaWorkoutReservation,
            agendaConsultingReservation.deleteConsultingReservation(reservation),
            id
        )
    }

    /**
     * Returns a new [Agenda] deleting a [reservation] from the list of [WorkoutReservation]
     */
    fun deleteWorkoutReservation(reservation: WorkoutReservation): Agenda {
        return Agenda(
            agendaWorkoutReservation.deleteWorkoutReservation(reservation),
            agendaConsultingReservation,
            id
        )
    }

    /**
     * Returns a list of [ConsultingReservation]
     */
    fun retrieveConsultingReservation(): List<ConsultingReservation> {
        return agendaConsultingReservation.consultingReservationList
    }

    /**
     * Returns a list of [WorkoutReservation]
     */
    fun retrieveWorkoutReservation(): List<WorkoutReservation> {
        return agendaWorkoutReservation.workoutReservationList
    }
}
