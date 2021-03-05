package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.assertions.fail
import io.kotest.core.spec.style.FreeSpec
import io.kotest.fp.success
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
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
    val freelancerId = "0001"
    val invalidFreelancerId = ""
    var member = Member("Mario", "Rossi", UUID.randomUUID())

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
            val requestConsultingMap = consultingManager
                .produce(createConsulting)
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
})
