package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.core.spec.style.FreeSpec
import java.util.Calendar
import java.util.UUID

class UseCaseTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    val ledgerId = UUID.randomUUID()
    val validFreelancer = "freelancer"
    val validAim = "Aim"
    val firstName = "Mario"
    val lastName = "Rossi"
    val memberId = UUID.randomUUID()
    val calendar = Calendar.getInstance()
    val validYear = 2022
    val validYearLate = 2028
    val feb = Calendar.FEBRUARY
    val day = 25
    calendar.set(validYear, feb, day)
    val validDate = calendar.time
    calendar.set(validYearLate, feb, day)
    val validDateLate = calendar.time
    val successMessage = "Everything fine"

    "Command and Query use case, given valid data, should" - {
        "create, close, update and delete reservations" - {
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
                .requestUpdateConsultingReservation(reservationOConsId, "newF", validDateLate)
            assert(resultCUp == successMessage)
            val resultCDel = commandUseCase.requestDeleteConsultingReservation(reservationOConsId, memberId)
            assert(resultCDel == successMessage)

            val resultWCre = commandUseCase
                .requestCreateWorkoutReservation(validAim, validDate, firstName, lastName, memberId)
            assert(resultWCre == successMessage)
            val reservationWorkId = queryUseCase.retrieveAgendaWorkoutReservation().first().reservationId
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
})
