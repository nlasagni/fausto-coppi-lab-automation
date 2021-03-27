package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.maps.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceeded
import java.util.Calendar
import java.util.UUID

class EventStoreTest : FreeSpec({
    val reservationId = UUID.randomUUID()
    val agendaID = UUID.randomUUID()
    val freelancerID = UUID.randomUUID()
    val member = Member("Mario", "Rossi", UUID.randomUUID())

    val calendar = Calendar.getInstance()
    val year = 2023
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
                CreateConsultingReservationRequest(
                    reservationId,
                    freelancerID,
                    validDate,
                    member.firstName,
                    member.lastName,
                    member.id
                ),
                ConsultingReservationManager(agendaID, ledgerID, mapOf())
            )
            eventStore.get().shouldNotBeEmpty()
            eventStore.getStream(reservationId).shouldNotBeEmpty()
            eventStore.getStream(reservationId).first().shouldBeInstanceOf<RequestSucceeded>()
        }
        "be appended to the list of event associated to him if the aggregate already exist" - {
            eventStore.evolve(
                aggregateId,
                CreateConsultingReservationRequest(
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
                UpdateConsultingReservationRequest(
                    UUID.randomUUID(),
                    reservationId,
                    UUID.randomUUID(),
                    validDate,
                ),
                ConsultingReservationManager(agendaID, ledgerID, mapOf())
            )
            eventStore.get().shouldNotBeEmpty()
            eventStore.getStream(reservationId).size.shouldBe(2)
        }
    }
})
