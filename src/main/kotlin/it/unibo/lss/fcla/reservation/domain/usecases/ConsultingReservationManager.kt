package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateFreelancerEvent
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.member.MemberLedger
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceededEvent
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberLedgerProjection
import java.util.UUID

/**
 * An implementation of [Producer] that handle consulting reservation
 */
class ConsultingReservationManager(private val agenda: Agenda, private val ledger: MemberLedger) : Producer {

    constructor(agendaId: UUID, ledgerId: UUID, events: Map<UUID, List<Event>>) :
        this(
            computeAgenda(agendaId, events.getOrDefault(agendaId, listOf())),
            computeMemberLedger(ledgerId, events.getOrDefault(ledgerId, listOf()))
        )

    private companion object {
        private fun computeAgenda(agendaId: UUID, events: List<Event>): Agenda {
            val agendaProjection = AgendaProjection(agendaId)
            return events.fold(agendaProjection.init) { agenda, event -> agendaProjection.update(agenda, event) }
        }
        private fun computeMemberLedger(ledgerId: UUID, events: List<Event>): MemberLedger {
            val ledgerProjection = MemberLedgerProjection(ledgerId)
            return events.fold(ledgerProjection.init) { ledger, event -> ledgerProjection.update(ledger, event) }
        }
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [CloseConsultingReservationEvent] occurs.
     */
    private fun closeConsultingReservation(event: CloseConsultingReservationEvent):
        Map<UUID, List<Event>> {
            val retrievedReservation = retrieveReservation(event.reservationId)
                ?: return errorInRequest(event.id, RequestFailedMessages.reservationNotFound)
            val closedReservation: CloseConsultingReservation
            try {
                closedReservation = CloseConsultingReservation(
                    retrievedReservation.date,
                    retrievedReservation.freelancerId,
                    retrievedReservation.id
                )
            } catch (exception: ConsultingReservationFreelancerCannotBeEmpty) {
                return errorInRequest(event.id, RequestFailedMessages.emptyConsultingFreelancer)
            }
            val agendaDeleteReservationEvent =
                AgendaDeleteConsultingReservationEvent(UUID.randomUUID(), retrievedReservation)
            val agendaAddReservationEvent =
                AgendaAddConsultingReservationEvent(UUID.randomUUID(), closedReservation)
            val member = ledger.retrieveMemberWithConsultingReservation(retrievedReservation)
            val memberDeleteReservationEvent =
                MemberDeleteConsultingReservationEvent(UUID.randomUUID(), retrievedReservation)
            val memberAddReservationEvent =
                MemberAddConsultingReservationEvent(UUID.randomUUID(), closedReservation)
            return mapOf(
                agenda.id to listOf(agendaDeleteReservationEvent, agendaAddReservationEvent),
                member.id to listOf(memberDeleteReservationEvent, memberAddReservationEvent),
                event.id to listOf(RequestSucceededEvent(UUID.randomUUID(), event.id))
            )
        }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [CreateConsultingReservationEvent] occurs.
     */
    private fun createConsultingReservation(
        event: CreateConsultingReservationEvent
    ): Map<UUID, List<Event>> {
        val openConsulting: OpenConsultingReservation
        try {
            openConsulting = OpenConsultingReservation(
                event.date,
                event.freelancer,
                event.memberId
            )
        } catch (exception: ConsultingReservationFreelancerCannotBeEmpty) {
            return errorInRequest(event.id, RequestFailedMessages.emptyConsultingFreelancer)
        } catch (exception: OpenReservationMustNotHavePastDate) {
            return errorInRequest(event.id, RequestFailedMessages.pastDateInReservation)
        }
        val agendaAddReservationEvent =
            AgendaAddConsultingReservationEvent(UUID.randomUUID(), openConsulting)
        val memberAddReservationEvent =
            MemberAddConsultingReservationEvent(UUID.randomUUID(), openConsulting)
        val resultMap: Map<UUID, List<Event>> = mapOf(
            agenda.id to listOf(agendaAddReservationEvent),
            event.memberId to listOf(memberAddReservationEvent),
            event.id to listOf(RequestSucceededEvent(UUID.randomUUID(), event.id))
        )
        return try {
            ledger.retrieveAllMembers().first { member -> member.id == event.memberId }
            resultMap
        } catch (exception: NoSuchElementException) {
            resultMap + (
                ledger.id to listOf(
                    LedgerAddMemberEvent(
                        UUID.randomUUID(),
                        Member(event.firstName, event.lastName, event.memberId)
                    )
                )
                )
        }
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [DeleteConsultingReservationEvent] occurs.
     */
    private fun deleteConsultingReservation(event: DeleteConsultingReservationEvent): Map<UUID, List<Event>> {
        val retrievedReservation = retrieveReservation(event.reservationId)
            ?: return errorInRequest(event.id, RequestFailedMessages.reservationNotFound)
        val agendaDeleteReservationEvent =
            AgendaDeleteConsultingReservationEvent(UUID.randomUUID(), retrievedReservation)
        val memberDeleteReservationEvent =
            MemberDeleteConsultingReservationEvent(UUID.randomUUID(), retrievedReservation)
        return mapOf(
            agenda.id to listOf(agendaDeleteReservationEvent),
            event.memberId to listOf(memberDeleteReservationEvent),
            event.id to listOf(RequestSucceededEvent(UUID.randomUUID(), event.id))
        )
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate when [UpdateConsultingReservationEvent] occurs.
     */
    private fun updateConsultingReservation(event: UpdateConsultingReservationEvent): Map<UUID, List<Event>> {
        val retrievedReservation = retrieveReservation(event.reservationId)
            ?: return errorInRequest(event.id, RequestFailedMessages.reservationNotFound)
        if (retrievedReservation is CloseConsultingReservation) {
            return errorInRequest(event.id, RequestFailedMessages.noUpdateToCloseReservation)
        }
        try {
            OpenConsultingReservation(
                retrievedReservation.date,
                retrievedReservation.freelancerId,
                retrievedReservation.id
            )
        } catch (exception: ConsultingReservationFreelancerCannotBeEmpty) {
            return errorInRequest(event.id, RequestFailedMessages.emptyConsultingFreelancer)
        } catch (exception: OpenReservationMustNotHavePastDate) {
            return errorInRequest(event.id, RequestFailedMessages.pastDateInReservation)
        }
        val updateConsultingFreelancerEvent =
            ConsultingReservationUpdateFreelancerEvent(UUID.randomUUID(), event.freelancer)
        val updateConsultingDateEvent =
            ConsultingReservationUpdateDateEvent(UUID.randomUUID(), event.date)
        return mapOf(
            event.reservationId to listOf(updateConsultingFreelancerEvent, updateConsultingDateEvent),
            event.id to listOf(RequestSucceededEvent(UUID.randomUUID(), event.id))
        )
    }

    /**
     * Returns a [Map] with the [UUID] of the aggregate as key and a [List] of [Event] as value for
     * the aggregate based on the specific occurred [event] which is related to the consulting reservation.
     */
    override fun produce(event: Event): Map<UUID, List<Event>> = when (event) {
        is CloseConsultingReservationEvent -> closeConsultingReservation(event)
        is CreateConsultingReservationEvent -> createConsultingReservation(event)
        is DeleteConsultingReservationEvent -> deleteConsultingReservation(event)
        is UpdateConsultingReservationEvent -> updateConsultingReservation(event)
        else -> mapOf()
    }

    /**
     * Returns a [Map] with the [UUID] of the request event as key and a [List] of error
     * [Event] as value, given the [eventId] and the [error] message.
     */
    private fun errorInRequest(eventId: UUID, error: String): Map<UUID, List<Event>> {
        return mapOf(
            eventId to listOf(
                RequestFailedEvent(
                    UUID.randomUUID(),
                    eventId,
                    error
                )
            )
        )
    }

    /**
     * Return a nullable [ConsultingReservation] given its [reservationId]
     */
    private fun retrieveReservation(reservationId: UUID): ConsultingReservation? {
        return agenda.retrieveConsultingReservation()
            .firstOrNull { consultingReservation -> reservationId == consultingReservation.id }
    }
}
