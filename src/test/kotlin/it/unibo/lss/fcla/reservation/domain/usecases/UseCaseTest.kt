package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.core.spec.style.FreeSpec
import org.junit.jupiter.api.assertThrows
import java.util.Calendar
import java.util.UUID

class UseCaseTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    val ledgerId = UUID.randomUUID()
    val validFreelancer = UUID.randomUUID()
    val invalidFreelancer = UUID( 0 , 0 )
    val validAim = "Aim"
    val invalidAim = ""
    val firstName = "Mario"
    val lastName = "Rossi"
    val memberId = UUID.randomUUID()
    val calendar = Calendar.getInstance()
    val invalidYear = 2020
    val validYear = 2022
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

    "Command and Query use case, given valid data, should" - {
        "create, close, update and delete consulting reservations" - {
            val eventStore = EventStore()
            val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore)
            val queryUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)
            val resultCCre = commandUseCase
                .requestCreateConsultingReservation(validFreelancer, validDate, firstName, lastName, memberId)
            assert(resultCCre == successMessage)
            val reservationConsId = queryUseCase.retrieveAgendaConsultingReservation().first().reservationId
            val resMemberList = queryUseCase.retrieveMemberConsultingReservations(memberId)
            assert(resMemberList.isNotEmpty())
            val resultCClose = commandUseCase.requestCloseConsultingReservation(reservationConsId, memberId)
            assert(resultCClose == successMessage)
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
            assert(resultCUp == successMessage)
            val resultCDel = commandUseCase.requestDeleteConsultingReservation(reservationOConsId, memberId)
            assert(resultCDel == successMessage)
        }
        "create, close, update and delete workout reservations" - {
            val eventStore = EventStore()
            val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore)
            val queryUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)
            val resultWCre = commandUseCase
                .requestCreateWorkoutReservation(validAim, validDate, firstName, lastName, memberId)
            assert(resultWCre == successMessage)
            val reservationWorkId = queryUseCase.retrieveAgendaWorkoutReservation().first().reservationId
            val resMemberWList = queryUseCase.retrieveMemberWorkoutReservations(memberId)
            assert(resMemberWList.isNotEmpty())
            val resultW = commandUseCase.requestCloseWorkoutReservation(reservationWorkId, memberId)
            assert(resultW == successMessage)
            commandUseCase
                .requestCreateWorkoutReservation(validAim, validDate, firstName, lastName, memberId)
            val reservationOWorkId = queryUseCase.retrieveAgendaWorkoutReservation()
                .first { resDate -> queryUseCase.retrieveWorkoutReservation(resDate.reservationId).isOpen }
                .reservationId
            val resultWUp = commandUseCase
                .requestUpdateWorkoutReservation(reservationOWorkId, "newAim", validDateLate)
            assert(resultWUp == successMessage)
            val resultWDel = commandUseCase.requestDeleteWorkoutReservation(reservationOWorkId, memberId)
            assert(resultWDel == successMessage)
        }
    }
    "Command and Query use case, given invalid data, should" - {
        "fail giving the specific error for consulting reservation" - {
            val eventStore = EventStore()
            val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore)
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestCreateConsultingReservation(invalidFreelancer, validDate, firstName, lastName, memberId)
            }
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestCreateConsultingReservation(validFreelancer, invalidDate, firstName, lastName, memberId)
            }
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestCloseConsultingReservation(UUID.randomUUID(), memberId)
            }
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestUpdateConsultingReservation(UUID.randomUUID(), validFreelancer, validDateLate)
            }
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestDeleteConsultingReservation(UUID.randomUUID(), memberId)
            }

            val queryUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)
            assert(queryUseCase.retrieveAgendaConsultingReservation().isEmpty())
            assertThrows<RequestFailedException> {
                queryUseCase
                    .retrieveMemberConsultingReservations(UUID.randomUUID())
            }
            assertThrows<RequestFailedException> {
                queryUseCase
                    .retrieveConsultingReservation(UUID.randomUUID())
            }
        }
        "fail giving the specific error for workout reservation" - {
            val eventStore = EventStore()
            val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore)
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestCreateWorkoutReservation(invalidAim, validDate, firstName, lastName, memberId)
            }
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestCreateWorkoutReservation(validAim, invalidDate, firstName, lastName, memberId)
            }
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestCloseWorkoutReservation(UUID.randomUUID(), memberId)
            }
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestUpdateWorkoutReservation(UUID.randomUUID(), validAim, validDateLate)
            }
            assertThrows<RequestFailedException> {
                commandUseCase
                    .requestDeleteWorkoutReservation(UUID.randomUUID(), memberId)
            }
            val queryUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)
            assert(queryUseCase.retrieveAgendaWorkoutReservation().isEmpty())
            assertThrows<RequestFailedException> {
                queryUseCase
                    .retrieveMemberWorkoutReservations(UUID.randomUUID())
            }
            assertThrows<RequestFailedException> {
                queryUseCase
                    .retrieveWorkoutReservation(UUID.randomUUID())
            }
        }
    }
})
