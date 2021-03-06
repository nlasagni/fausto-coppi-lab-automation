package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.assertions.fail
import io.kotest.core.spec.style.FreeSpec
import io.kotest.fp.success
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateFreelancerEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceededEvent
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import java.util.Calendar
import java.util.UUID

class ConsultingReservationManagerTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    val ledgerId = UUID.randomUUID()
    val consultingManager = ConsultingReservationManager(agendaId, ledgerId, mapOf())
    val calendar = Calendar.getInstance()
    val year = 2022
    val feb = Calendar.FEBRUARY
    val day = 25
    calendar.set(year, feb, day)
    val validDate = calendar.time
    val invalidYear = 2020
    calendar.set(invalidYear, feb, day)
    val invalidDate = calendar.time
    calendar.set(year, Calendar.MARCH, day)
    val updatedDate = calendar.time
    val freelancerId = "0001"
    val invalidFreelancerId = ""
    var member = Member("Mario", "Rossi", UUID.randomUUID())

    "A CloseConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce an error if reservation not found" - {
            val closeInvalidConsulting = CloseConsultingReservationEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                member.id
            )

            val failingCloseRequest = consultingManager
                .produce(closeInvalidConsulting)[closeInvalidConsulting.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingCloseRequest as RequestFailedEvent
            assert(failEvent.requestId == closeInvalidConsulting.id)
            assert(failEvent.message == RequestFailedMessages.reservationNotFound)
        }
        "produce event for agenda, member" - {
            val createConsulting = CreateConsultingReservationEvent(
                UUID.randomUUID(),
                freelancerId,
                validDate,
                member.firstName,
                member.lastName,
                member.id
            )

            val createMap = consultingManager.produce(createConsulting)
            val createRes = createMap[createConsulting.id]?.first() ?: fail("Success event not found")
            assert(createRes is RequestSucceededEvent)

            val agendaProjection = AgendaProjection(agendaId)
            val agenda = createMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(agenda.retrieveConsultingReservation().first() is OpenConsultingReservation)
            val resId = agenda.retrieveConsultingReservation().first().id

            val manager = ConsultingReservationManager(agendaId, ledgerId, createMap)
            val closeConsulting = CloseConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                createConsulting.memberId
            )
            val requestConsultingMap = manager.produce(closeConsulting)
            requestConsultingMap[closeConsulting.id]?.first() ?: fail("Success event not found")

            val agendaDeleteFromList = requestConsultingMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaDeleteFromList.any { event -> event is AgendaDeleteConsultingReservationEvent })
            assert(agendaDeleteFromList.any { event -> event is AgendaAddConsultingReservationEvent })

            val memberList = requestConsultingMap[member.id] ?: fail("Member events not found")
            assert(memberList.any { event -> event is MemberDeleteConsultingReservationEvent })
            assert(memberList.any { event -> event is MemberAddConsultingReservationEvent })
            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(closeConsulting)
            resOldMemberMap[closeConsulting.id]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
        }
    }
    "A CreateConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val createInvalidFreelancerConsulting = CreateConsultingReservationEvent(
                UUID.randomUUID(),
                invalidFreelancerId,
                validDate,
                member.firstName,
                member.lastName,
                member.id
            )

            val failingRequestDueToFreelancer = consultingManager
                .produce(createInvalidFreelancerConsulting)[createInvalidFreelancerConsulting.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToFreelancer as RequestFailedEvent
            assert(failEvent.requestId == createInvalidFreelancerConsulting.id)
            assert(failEvent.message == RequestFailedMessages.emptyConsultingFreelancer)

            val createInvalidDateConsulting = CreateConsultingReservationEvent(
                UUID.randomUUID(),
                freelancerId,
                invalidDate,
                member.firstName,
                member.lastName,
                member.id
            )
            val failingRequestDueToDate = consultingManager
                .produce(createInvalidDateConsulting)[createInvalidDateConsulting.id]?.first()
                ?: fail("Error in request")
            val failEventDate = failingRequestDueToDate as RequestFailedEvent
            assert(failEventDate.requestId == createInvalidDateConsulting.id)
            assert(failEventDate.message == RequestFailedMessages.pastDateInReservation)
        }
        "produce event for agenda, member and ledger" - {
            val createConsulting = CreateConsultingReservationEvent(
                UUID.randomUUID(),
                freelancerId,
                validDate,
                member.firstName,
                member.lastName,
                member.id
            )
            val requestConsultingMap = consultingManager.produce(createConsulting)
            requestConsultingMap[createConsulting.id]?.first() ?: fail("Success event not found")
            val agendaList = requestConsultingMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaList.first() is AgendaAddConsultingReservationEvent)
            val ledgerList = requestConsultingMap[ledgerId] ?: fail("Ledger events not found")
            assert(ledgerList.first() is LedgerAddMemberEvent)
            val memberList = requestConsultingMap[member.id] ?: fail("Member events not found")
            assert(memberList.first() is MemberAddConsultingReservationEvent)
            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(createConsulting)
            resOldMemberMap[createConsulting.id]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
        }
    }
    "An UpdateConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val updateInvalidConsultingRequest = UpdateConsultingReservationEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                freelancerId,
                updatedDate
            )
            val failingRequestDueToRequestNotFound = consultingManager
                .produce(updateInvalidConsultingRequest)[updateInvalidConsultingRequest.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailedEvent
            assert(failEvent.requestId == updateInvalidConsultingRequest.id)
            assert(failEvent.message == RequestFailedMessages.reservationNotFound)
        }
        "produce event for agenda, member" - {
            val createConsulting = CreateConsultingReservationEvent(
                UUID.randomUUID(),
                freelancerId,
                validDate,
                member.firstName,
                member.lastName,
                member.id
            )

            val consultingMap = consultingManager.produce(createConsulting)
            val createRes = consultingMap[createConsulting.id]?.first() ?: fail("Success event not found")
            assert(createRes is RequestSucceededEvent)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveConsultingReservation().first().freelancerId == freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingMap)

            val updateConsultingDate = UpdateConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                createConsulting.freelancer,
                updatedDate
            )
            val requestConsultingMap = manager.produce(updateConsultingDate)
            val updateEventMap = requestConsultingMap[updateConsultingDate.id]?.first() ?: fail("Success event not found")
            assert(updateEventMap is RequestSucceededEvent)

            val reservationEventMap = requestConsultingMap[resId] ?: fail("Reservation events not found")
            assert(reservationEventMap.any { event -> event is ConsultingReservationUpdateFreelancerEvent })
            assert(reservationEventMap.any { event -> event is ConsultingReservationUpdateDateEvent })
            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(updateConsultingDate)
            resOldMemberMap[updateConsultingDate.id]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
        }
    }
    "A DeleteConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val deleteInvalidConsultingRequest = DeleteConsultingReservationEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                member.id
            )
            val failingRequestDueToRequestNotFound = consultingManager
                .produce(deleteInvalidConsultingRequest)[deleteInvalidConsultingRequest.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailedEvent
            assert(failEvent.requestId == deleteInvalidConsultingRequest.id)
            assert(failEvent.message == RequestFailedMessages.reservationNotFound)
        }
        "produce event for agenda, member" - {
            val createConsulting = CreateConsultingReservationEvent(
                UUID.randomUUID(),
                freelancerId,
                validDate,
                member.firstName,
                member.lastName,
                member.id
            )

            val createMap = consultingManager.produce(createConsulting)
            createMap[createConsulting.id]?.first() ?: fail("Success event not found")
            val manager = ConsultingReservationManager(agendaId, ledgerId, createMap)

            val agendaProjection = AgendaProjection(agendaId)
            val agenda = createMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(agenda.retrieveConsultingReservation().first().freelancerId == freelancerId)
            val resId = agenda.retrieveConsultingReservation().first().id

            val deleteConsulting = DeleteConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                createConsulting.memberId
            )

            val requestConsultingMap = manager.produce(deleteConsulting)
            requestConsultingMap[deleteConsulting.id]?.first() ?: fail("Success event not found")

            val agendaList = requestConsultingMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaList.first() is AgendaDeleteConsultingReservationEvent)

            val memberList = requestConsultingMap[member.id] ?: fail("Member events not found")
            assert(memberList.first() is MemberDeleteConsultingReservationEvent)
            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(deleteConsulting)
            resOldMemberMap[deleteConsulting.id]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
        }
    }
})
