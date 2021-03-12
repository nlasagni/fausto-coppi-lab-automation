package it.unibo.lss.fcla

import it.unibo.lss.fcla.reservation.domain.usecases.CommandReservationUseCase
import it.unibo.lss.fcla.reservation.domain.usecases.EventStore
import it.unibo.lss.fcla.reservation.domain.usecases.QueryReservationUseCase
import java.util.Calendar
import java.util.UUID

class MainClass {
    companion object {
        private const val year = 2021
        private const val day = 13
        private val agendaId = UUID.randomUUID()
        private val ledgerId = UUID.randomUUID()
        private val eventStore = EventStore()
        private val freelancer = UUID.randomUUID()
        private const val month = Calendar.APRIL
        private val calendar = Calendar.getInstance()
        private const val memberFirstName = "Mario"
        private const val memberLastName = "Rossi"
        private val memberId = UUID.randomUUID()
    }
    fun createReservation() {
        calendar.set(year, month, day)
        val reservationDate = calendar.time
        val commandReservationUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore)
        val queryReservationUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)
        println(
            "Result of the request: " + commandReservationUseCase.requestCreateConsultingReservation(
                freelancer,
                reservationDate,
                memberFirstName,
                memberLastName,
                memberId
            )
        )
        val retrievedReservation =
            queryReservationUseCase.retrieveMemberConsultingReservations(memberId).first()
        println("Retrieved reservation: $retrievedReservation")
        val reservationDetails =
            queryReservationUseCase.retrieveConsultingReservation(retrievedReservation.reservationId)
        println("Reservation details: $reservationDetails")
    }
}

fun main() {
    MainClass().createReservation()
}
