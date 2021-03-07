package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceededEvent
import java.util.Calendar
import java.util.UUID

class EventStoreTest : FreeSpec({
    val reservationId = UUID.randomUUID()
    val agendaID = UUID.randomUUID()
    val freelancerID = "" + UUID.randomUUID()
    val member = Member("Mario", "Rossi", UUID.randomUUID())

    val calendar = Calendar.getInstance()
    val year = 2022
    val feb = Calendar.FEBRUARY
    val day = 25
    calendar.set(year, feb, day)
    val validDate = calendar.time

    val ledgerID = UUID.randomUUID()
    val eventStore = EventStore()
    val aggregateId = UUID.randomUUID()
    val aggregateId2 = UUID.randomUUID()

    "A new event should" - {
        "be appended to the eventStore map" - {
            eventStore.evolve(
                aggregateId,
                CreateConsultingReservationEvent(
                    reservationId,
                    freelancerID,
                    validDate,
                    member.firstName,
                    member.lastName,
                    member.id
                ),
                ConsultingReservationManager(agendaID, ledgerID, mapOf())
            )
            assert(eventStore.get().isNotEmpty())
            assert(eventStore.getStream(reservationId).isNotEmpty())
            assert(eventStore.getStream(reservationId).first() is RequestSucceededEvent)
        }
        "be appended to the list of event associated to him if the aggregate already exist" - {
            eventStore.evolve(
                aggregateId,
                CreateConsultingReservationEvent(
                    reservationId,
                    freelancerID,
                    validDate,
                    member.firstName,
                    member.lastName,
                    member.id
                ),
                ConsultingReservationManager(agendaID, ledgerID, mapOf())
            )

            eventStore.evolve(
                aggregateId2,
                UpdateConsultingReservationEvent(
                    UUID.randomUUID(),
                    reservationId,
                    "4543",
                    validDate,
                ),
                ConsultingReservationManager(agendaID, ledgerID, mapOf())
            )
            assert(eventStore.get().isNotEmpty())
            assert(eventStore.getStream(reservationId).size == 2)
        }
    }
})
