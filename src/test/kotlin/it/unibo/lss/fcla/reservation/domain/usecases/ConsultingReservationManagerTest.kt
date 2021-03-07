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
    val member1 = Member("Mario", "Bianchi", UUID.randomUUID())

    val createValidConsultingReservation = CreateConsultingReservationEvent(
        UUID.randomUUID(),
        freelancerId,
        validDate,
        member.firstName,
        member.lastName,
        member.id
    )

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
        "produce error if close an has invalid member or member not found" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveConsultingReservation().first().freelancerId == freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id

            val closeInvalidMemberConsultingRequest = CloseConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val managerWithReservation = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)
            val failingRequestDueToRequestNotFound = managerWithReservation
                .produce(closeInvalidMemberConsultingRequest)[closeInvalidMemberConsultingRequest.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailedEvent
            assert(failEvent.requestId == closeInvalidMemberConsultingRequest.id)
            assert(failEvent.message == RequestFailedMessages.memberNotFound)

            val createValidConsultingReservationMember1 = CreateConsultingReservationEvent(
                UUID.randomUUID(),
                freelancerId,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val mapWithAnotherReservations = managerWithReservation.produce(createValidConsultingReservationMember1)
            val mapWith2Reservation = mapWithAnotherReservations.entries.fold(consultingManagerMap) {
                map, entries ->
                val list = map.getOrDefault(entries.key, listOf()) + entries.value
                map + (entries.key to list)
            }
            val managerWith2Reservations = ConsultingReservationManager(agendaId, ledgerId, mapWith2Reservation)

            val closeWrongMemberConsultingRequest = CloseConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val failingRequestDueToWrongMember = managerWith2Reservations
                .produce(closeWrongMemberConsultingRequest)[closeWrongMemberConsultingRequest.id]?.first()
                ?: fail("Error in request")
            val failEventWrongMember = failingRequestDueToWrongMember as RequestFailedEvent
            assert(failEventWrongMember.requestId == closeWrongMemberConsultingRequest.id)
            assert(failEventWrongMember.message == RequestFailedMessages.wrongMember)
        }
        "produce event for agenda, member" - {
            val createMap = consultingManager.produce(createValidConsultingReservation)
            val createRes = createMap[createValidConsultingReservation.id]?.first() ?: fail("Success event not found")
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
                createValidConsultingReservation.memberId
            )
            val requestConsultingMap = manager.produce(closeConsulting)
            requestConsultingMap[closeConsulting.id]?.first() ?: fail("Success event not found")

            val agendaDeleteFromList = requestConsultingMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaDeleteFromList.any { event -> event is AgendaDeleteConsultingReservationEvent })
            assert(agendaDeleteFromList.any { event -> event is AgendaAddConsultingReservationEvent })

            val memberList = requestConsultingMap[member.id] ?: fail("Member events not found")
            assert(memberList.any { event -> event is MemberDeleteConsultingReservationEvent })
            assert(memberList.any { event -> event is MemberAddConsultingReservationEvent })

            val closeResult = requestConsultingMap[closeConsulting.id]?.first() ?: fail("Success event not found")
            assert(closeResult is RequestSucceededEvent)

            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(closeConsulting)
            val closeFail = resOldMemberMap[closeConsulting.id]?.first() ?: fail("Success event not found")
            assert(closeFail is RequestFailedEvent)
            val failMessage = closeFail as RequestFailedEvent
            assert(failMessage.message == RequestFailedMessages.alreadyCloseReservation)
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

            val requestConsultingMap = consultingManager.produce(createValidConsultingReservation)
            requestConsultingMap[createValidConsultingReservation.id]?.first() ?: fail("Success event not found")
            val agendaList = requestConsultingMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaList.first() is AgendaAddConsultingReservationEvent)
            val ledgerList = requestConsultingMap[ledgerId] ?: fail("Ledger events not found")
            assert(ledgerList.first() is LedgerAddMemberEvent)
            val memberList = requestConsultingMap[member.id] ?: fail("Member events not found")
            assert(memberList.first() is MemberAddConsultingReservationEvent)
            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(createValidConsultingReservation)
            val createResult = resOldMemberMap[createValidConsultingReservation.id]?.first() ?: fail("Success event not found")
            assert(createResult is RequestSucceededEvent)

            val createSecondValidConsultingReservation = CreateConsultingReservationEvent(
                UUID.randomUUID(),
                freelancerId,
                validDate,
                member.firstName,
                member.lastName,
                member.id
            )
            val mapWithExistingMember = consultingFullManager.produce(createSecondValidConsultingReservation)
            mapWithExistingMember[ledgerId]?.first() ?: success()
        }
    }
    "An UpdateConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce empty map if event is not valid" - {
            val invalidEvent = ConsultingReservationUpdateDateEvent(UUID.randomUUID(), validDate)
            val emptyMap = consultingManager.produce(invalidEvent)
            assert(emptyMap.isEmpty())
        }
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
        "produce an error if are present empty parameters" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)
            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveConsultingReservation().first().freelancerId == freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)

            val updateInvalidConsultingDueToEmptyFreelancerRequest = UpdateConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                "",
                updatedDate
            )
            val requestFailAimConsultingMap = manager.produce(updateInvalidConsultingDueToEmptyFreelancerRequest)

            val failUpdate =
                requestFailAimConsultingMap[updateInvalidConsultingDueToEmptyFreelancerRequest.id]
                    ?.first() ?: fail("Success event not found")
            assert(failUpdate is RequestFailedEvent)
            val failEvent = failUpdate as RequestFailedEvent
            assert(failEvent.requestId == updateInvalidConsultingDueToEmptyFreelancerRequest.id)
            assert(failEvent.message == RequestFailedMessages.emptyConsultingFreelancer)

            val updateInvalidConsultingDueToPastDateRequest = UpdateConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                freelancerId,
                invalidDate
            )
            val requestFailDateConsultingMap = manager.produce(updateInvalidConsultingDueToPastDateRequest)

            val failUpdatePastDate =
                requestFailDateConsultingMap[updateInvalidConsultingDueToPastDateRequest.id]
                    ?.first() ?: fail("Success event not found")
            assert(failUpdatePastDate is RequestFailedEvent)
            val failEventPastDate = failUpdatePastDate as RequestFailedEvent
            assert(failEventPastDate.requestId == updateInvalidConsultingDueToPastDateRequest.id)
            assert(failEventPastDate.message == RequestFailedMessages.pastDateInReservation)
        }
        "produce an error if a closeConsultingReservation is updated" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveConsultingReservation().first().freelancerId == freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id

            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)

            val closeConsulting = CloseConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                createValidConsultingReservation.memberId
            )
            val requestConsultingMap = manager.produce(closeConsulting)

            val managerFailUpdate = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)

            val updateConsultingDate = UpdateConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                freelancerId,
                updatedDate
            )
            val resUpdateFailMap = managerFailUpdate.produce(updateConsultingDate)
            val failUpdate =
                resUpdateFailMap[updateConsultingDate.id]?.first() ?: fail("Success event not found")
            assert(failUpdate is RequestFailedEvent)
            val failMessage = failUpdate as RequestFailedEvent
            assert(failMessage.message == RequestFailedMessages.noUpdateToCloseReservation)
        }
        "produce event for agenda, member" - {
            val consultingMap = consultingManager.produce(createValidConsultingReservation)
            val createRes = consultingMap[createValidConsultingReservation.id]?.first() ?: fail("Success event not found")
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
                createValidConsultingReservation.freelancer,
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
            val failUpdate = resOldMemberMap[updateConsultingDate.id]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
            assert(failUpdate is RequestFailedEvent)
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
        "produce an error if member not found" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveConsultingReservation().first().freelancerId == freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id

            val deleteInvalidMemberConsultingRequest = DeleteConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)
            val failingRequestDueToRequestNotFound = manager
                .produce(deleteInvalidMemberConsultingRequest)[deleteInvalidMemberConsultingRequest.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailedEvent
            assert(failEvent.requestId == deleteInvalidMemberConsultingRequest.id)
            assert(failEvent.message == RequestFailedMessages.memberNotFound)

            val createValidConsultingReservationMember1 = CreateConsultingReservationEvent(
                UUID.randomUUID(),
                freelancerId,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val mapWithAnotherReservations = manager.produce(createValidConsultingReservationMember1)
            val mapWith2Reservation = mapWithAnotherReservations.entries.fold(consultingManagerMap) {
                map, entries ->
                val list = map.getOrDefault(entries.key, listOf()) + entries.value
                map + (entries.key to list)
            }
            val managerWith2Reservations = ConsultingReservationManager(agendaId, ledgerId, mapWith2Reservation)

            val deleteWrongMemberConsultingRequest = DeleteConsultingReservationEvent(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val failingRequestDueToWrongMember = managerWith2Reservations
                .produce(deleteWrongMemberConsultingRequest)[deleteWrongMemberConsultingRequest.id]?.first()
                ?: fail("Error in request")
            val failEventWrongMember = failingRequestDueToWrongMember as RequestFailedEvent
            assert(failEventWrongMember.requestId == deleteWrongMemberConsultingRequest.id)
            assert(failEventWrongMember.message == RequestFailedMessages.wrongMember)
        }
        "produce event for agenda and member" - {
            val createMap = consultingManager.produce(createValidConsultingReservation)
            createMap[createValidConsultingReservation.id]?.first() ?: fail("Success event not found")
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
                createValidConsultingReservation.memberId
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
