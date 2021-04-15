/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.reservation.persistence.RepositoryInMemory
import java.util.Calendar
import java.util.UUID

class UseCaseTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    val ledgerId = UUID.randomUUID()
    val validFreelancer = UUID.randomUUID()
    val invalidFreelancer = UUID(0, 0)
    val validAim = "Aim"
    val invalidAim = ""
    val firstName = "Mario"
    val lastName = "Rossi"
    val memberId = UUID.randomUUID()
    val calendar = Calendar.getInstance()
    val invalidYear = 2020
    val validYear = 2023
    val validYearLate = 2028
    val feb = Calendar.FEBRUARY
    val day = 25
    calendar.set(validYear, feb, day)
    val validDate = calendar.time
    calendar.set(validYearLate, feb, day)
    val validDateLate = calendar.time
    calendar.set(invalidYear, feb, day)
    val invalidDate = calendar.time
    val successMessage = "The request succeeded"
    val repository = RepositoryInMemory()

    "Command and Query use case, given valid data, should" - {
        "create, close, update and delete consulting reservations" - {
            val eventStore = EventStore()
            val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore, repository)
            val queryUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)
            val resultCCre = commandUseCase
                .requestCreateConsultingReservation(validFreelancer, validDate, firstName, lastName, memberId)
            resultCCre.shouldBe(successMessage)
            val reservationConsId = queryUseCase.retrieveAgendaConsultingReservation().first().reservationId
            val resMemberList = queryUseCase.retrieveMemberConsultingReservations(memberId)
            resMemberList.shouldNotBeEmpty()
            val resultCClose = commandUseCase.requestCloseConsultingReservation(reservationConsId, memberId)
            resultCClose.shouldBe(successMessage)
            commandUseCase
                .requestCreateConsultingReservation(validFreelancer, validDate, firstName, lastName, memberId)
            val reservationOConsId = queryUseCase.retrieveAgendaConsultingReservation()
                .first { resDate -> queryUseCase.retrieveConsultingReservation(resDate.reservationId).isOpen }
                .reservationId
            val resultCUp = commandUseCase
                .requestUpdateConsultingReservation(
                    reservationOConsId,
                    UUID.randomUUID(),
                    validDateLate
                )
            resultCUp.shouldBe(successMessage)
            val resultCDel = commandUseCase.requestDeleteConsultingReservation(reservationOConsId, memberId)
            resultCDel.shouldBe(successMessage)
        }
        "create, close, update and delete workout reservations" - {
            val eventStore = EventStore()
            val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore, repository)
            val queryUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)
            val resultWCre = commandUseCase
                .requestCreateWorkoutReservation(validAim, validDate, firstName, lastName, memberId)
            resultWCre.shouldBe(successMessage)
            val reservationWorkId = queryUseCase.retrieveAgendaWorkoutReservation().first().reservationId
            val resMemberWList = queryUseCase.retrieveMemberWorkoutReservations(memberId)
            resMemberWList.shouldNotBeEmpty()
            val resultW = commandUseCase.requestCloseWorkoutReservation(reservationWorkId, memberId)
            resultW.shouldBe(successMessage)
            commandUseCase
                .requestCreateWorkoutReservation(validAim, validDate, firstName, lastName, memberId)
            val reservationOWorkId = queryUseCase.retrieveAgendaWorkoutReservation()
                .first { resDate -> queryUseCase.retrieveWorkoutReservation(resDate.reservationId).isOpen }
                .reservationId
            val resultWUp = commandUseCase
                .requestUpdateWorkoutReservation(reservationOWorkId, "newAim", validDateLate)
            resultWUp.shouldBe(successMessage)
            val resultWDel = commandUseCase.requestDeleteWorkoutReservation(reservationOWorkId, memberId)
            resultWDel.shouldBe(successMessage)
        }
    }
    "Command and Query use case, given invalid data, should" - {
        "fail giving the specific error for consulting reservation" - {
            val eventStore = EventStore()
            val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore, repository)
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestCreateConsultingReservation(invalidFreelancer, validDate, firstName, lastName, memberId)
            }
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestCreateConsultingReservation(validFreelancer, invalidDate, firstName, lastName, memberId)
            }
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestCloseConsultingReservation(UUID.randomUUID(), memberId)
            }
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestUpdateConsultingReservation(UUID.randomUUID(), validFreelancer, validDateLate)
            }
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestDeleteConsultingReservation(UUID.randomUUID(), memberId)
            }

            val queryUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)
            queryUseCase.retrieveAgendaConsultingReservation().shouldBeEmpty()
            shouldThrow<RequestFailedException> {
                queryUseCase
                    .retrieveMemberConsultingReservations(UUID.randomUUID())
            }
            shouldThrow<RequestFailedException> {
                queryUseCase
                    .retrieveConsultingReservation(UUID.randomUUID())
            }
        }
        "fail giving the specific error for workout reservation" - {
            val eventStore = EventStore()
            val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore, repository)
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestCreateWorkoutReservation(invalidAim, validDate, firstName, lastName, memberId)
            }
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestCreateWorkoutReservation(validAim, invalidDate, firstName, lastName, memberId)
            }
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestCloseWorkoutReservation(UUID.randomUUID(), memberId)
            }
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestUpdateWorkoutReservation(UUID.randomUUID(), validAim, validDateLate)
            }
            shouldThrow<RequestFailedException> {
                commandUseCase
                    .requestDeleteWorkoutReservation(UUID.randomUUID(), memberId)
            }
            val queryUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)
            queryUseCase.retrieveAgendaWorkoutReservation().shouldBeEmpty()
            shouldThrow<RequestFailedException> {
                queryUseCase
                    .retrieveMemberWorkoutReservations(UUID.randomUUID())
            }
            shouldThrow<RequestFailedException> {
                queryUseCase
                    .retrieveWorkoutReservation(UUID.randomUUID())
            }
        }
    }
})
