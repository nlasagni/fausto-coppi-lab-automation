package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.assertions.fail
import io.kotest.core.spec.style.FreeSpec
import io.kotest.fp.success
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMember
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDate
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateFreelancer
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateConsultingReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailed
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceeded
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
    val freelancerId = UUID.randomUUID()
    val invalidFreelancerId = UUID(0, 0)
    var member = Member("Mario", "Rossi", UUID.randomUUID())
    val member1 = Member("Mario", "Bianchi", UUID.randomUUID())

    val createValidConsultingReservation = CreateConsultingReservationRequest(
        UUID.randomUUID(),
        freelancerId,
        validDate,
        member.firstName,
        member.lastName,
        member.id
    )

    "A CloseConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce an error if reservation not found" - {
            val closeInvalidConsulting = CloseConsultingReservationRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                member.id
            )

            val failingCloseRequest = consultingManager
                .produce(closeInvalidConsulting)[closeInvalidConsulting.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingCloseRequest as RequestFailed
            failEvent.requestId.shouldBe(closeInvalidConsulting.eventId)
            failEvent.message.shouldBe(RequestFailedMessages.reservationNotFound)
        }
        "produce error if close an has invalid member or member not found" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            ag.retrieveConsultingReservation().first().freelancerId.freelancerId.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id

            val closeInvalidMemberConsultingRequest = CloseConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val managerWithReservation = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)
            val failingRequestDueToRequestNotFound = managerWithReservation
                .produce(closeInvalidMemberConsultingRequest)[closeInvalidMemberConsultingRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailed
            failEvent.requestId.shouldBe(closeInvalidMemberConsultingRequest.eventId)
            failEvent.message.shouldBe(RequestFailedMessages.memberNotFound)

            val createValidConsultingReservationMember1 = CreateConsultingReservationRequest(
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

            val closeWrongMemberConsultingRequest = CloseConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val failingRequestDueToWrongMember = managerWith2Reservations
                .produce(closeWrongMemberConsultingRequest)[closeWrongMemberConsultingRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEventWrongMember = failingRequestDueToWrongMember as RequestFailed
            failEventWrongMember.requestId.shouldBe(closeWrongMemberConsultingRequest.eventId)
            failEventWrongMember.message.shouldBe(RequestFailedMessages.wrongMember)
        }
        "produce event for agenda, member" - {
            val createMap = consultingManager.produce(createValidConsultingReservation)
            val createRes = createMap[createValidConsultingReservation.eventId]?.first() ?: fail("Success event not found")
            createRes.shouldBeInstanceOf<RequestSucceeded>()

            val agendaProjection = AgendaProjection(agendaId)
            val agenda = createMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            agenda.retrieveConsultingReservation().first().shouldBeInstanceOf<OpenConsultingReservation>()
            val resId = agenda.retrieveConsultingReservation().first().id

            val manager = ConsultingReservationManager(agendaId, ledgerId, createMap)
            val closeConsulting = CloseConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                createValidConsultingReservation.memberId
            )
            val requestConsultingMap = manager.produce(closeConsulting)
            requestConsultingMap[closeConsulting.eventId]?.first() ?: fail("Success event not found")

            val agendaDeleteFromList = requestConsultingMap[agendaId] ?: fail("Agenda events not found")
            agendaDeleteFromList.shouldHaveSingleElement { event -> event is AgendaDeleteConsultingReservation }
            agendaDeleteFromList.shouldHaveSingleElement { event -> event is AgendaAddConsultingReservation }

            val memberList = requestConsultingMap[member.id] ?: fail("Member events not found")
            memberList.shouldHaveSingleElement { event -> event is MemberDeleteConsultingReservation }
            memberList.shouldHaveSingleElement { event -> event is MemberAddConsultingReservation }

            val closeResult = requestConsultingMap[closeConsulting.eventId]?.first() ?: fail("Success event not found")
            closeResult.shouldBeInstanceOf<RequestSucceeded>()

            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(closeConsulting)
            val closeFail = resOldMemberMap[closeConsulting.eventId]?.first() ?: fail("Success event not found")
            closeFail.shouldBeInstanceOf<RequestFailed>()
            closeFail.message.shouldBe(RequestFailedMessages.alreadyCloseReservation)
        }
    }
    "A CreateConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val createInvalidFreelancerConsulting = CreateConsultingReservationRequest(
                UUID.randomUUID(),
                invalidFreelancerId,
                validDate,
                member.firstName,
                member.lastName,
                member.id
            )

            val failingRequestDueToFreelancer = consultingManager
                .produce(createInvalidFreelancerConsulting)[createInvalidFreelancerConsulting.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToFreelancer as RequestFailed
            failEvent.requestId.shouldBe(createInvalidFreelancerConsulting.eventId)
            failEvent.message.shouldBe(RequestFailedMessages.emptyConsultingFreelancer)

            val createInvalidDateConsulting = CreateConsultingReservationRequest(
                UUID.randomUUID(),
                freelancerId,
                invalidDate,
                member.firstName,
                member.lastName,
                member.id
            )
            val failingRequestDueToDate = consultingManager
                .produce(createInvalidDateConsulting)[createInvalidDateConsulting.eventId]?.first()
                ?: fail("Error in request")
            val failEventDate = failingRequestDueToDate as RequestFailed
            failEventDate.requestId.shouldBe(createInvalidDateConsulting.eventId)
            failEventDate.message.shouldBe(RequestFailedMessages.pastDateInReservation)
        }
        "produce event for agenda, member and ledger" - {

            val requestConsultingMap = consultingManager.produce(createValidConsultingReservation)
            requestConsultingMap[createValidConsultingReservation.eventId]?.first() ?: fail("Success event not found")
            val agendaList = requestConsultingMap[agendaId] ?: fail("Agenda events not found")
            agendaList.first().shouldBeInstanceOf<AgendaAddConsultingReservation>()
            val ledgerList = requestConsultingMap[ledgerId] ?: fail("Ledger events not found")
            ledgerList.first().shouldBeInstanceOf<LedgerAddMember>()
            val memberList = requestConsultingMap[member.id] ?: fail("Member events not found")
            memberList.first().shouldBeInstanceOf<MemberAddConsultingReservation>()
            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(createValidConsultingReservation)
            val createResult = resOldMemberMap[createValidConsultingReservation.eventId]?.first() ?: fail("Success event not found")
            createResult.shouldBeInstanceOf<RequestSucceeded>()

            val createSecondValidConsultingReservation = CreateConsultingReservationRequest(
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
            val invalidEvent = ConsultingReservationUpdateDate(UUID.randomUUID(), validDate)
            val emptyMap = consultingManager.produce(invalidEvent)
            emptyMap.shouldBeEmpty()
        }
        "produce an error if parameters are not valid" - {
            val updateInvalidConsultingRequest = UpdateConsultingReservationRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                freelancerId,
                updatedDate
            )
            val failingRequestDueToRequestNotFound = consultingManager
                .produce(updateInvalidConsultingRequest)[updateInvalidConsultingRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailed
            failEvent.requestId.shouldBe(updateInvalidConsultingRequest.eventId)
            failEvent.message.shouldBe(RequestFailedMessages.reservationNotFound)
        }
        "produce an error if are present empty parameters" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)
            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            ag.retrieveConsultingReservation().first().freelancerId.freelancerId.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)

            val updateInvalidConsultingDueToEmptyFreelancerRequest = UpdateConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                invalidFreelancerId,
                updatedDate
            )
            val requestFailAimConsultingMap = manager.produce(updateInvalidConsultingDueToEmptyFreelancerRequest)

            val failUpdate =
                requestFailAimConsultingMap[updateInvalidConsultingDueToEmptyFreelancerRequest.eventId]
                    ?.first() ?: fail("Success event not found")
            failUpdate.shouldBeInstanceOf<RequestFailed>()
            failUpdate.requestId.shouldBe(updateInvalidConsultingDueToEmptyFreelancerRequest.eventId)
            failUpdate.message.shouldBe(RequestFailedMessages.emptyConsultingFreelancer)

            val updateInvalidConsultingDueToPastDateRequest = UpdateConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                freelancerId,
                invalidDate
            )
            val requestFailDateConsultingMap = manager.produce(updateInvalidConsultingDueToPastDateRequest)

            val failUpdatePastDate =
                requestFailDateConsultingMap[updateInvalidConsultingDueToPastDateRequest.eventId]
                    ?.first() ?: fail("Success event not found")
            failUpdatePastDate.shouldBeInstanceOf<RequestFailed>()
            failUpdatePastDate.requestId.shouldBe(updateInvalidConsultingDueToPastDateRequest.eventId)
            failUpdatePastDate.message.shouldBe(RequestFailedMessages.pastDateInReservation)
        }
        "produce an error if a closeConsultingReservation is updated" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            ag.retrieveConsultingReservation().first().freelancerId.freelancerId.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id

            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)

            val closeConsulting = CloseConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                createValidConsultingReservation.memberId
            )
            val requestConsultingMap = manager.produce(closeConsulting)

            val managerFailUpdate = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)

            val updateConsultingDate = UpdateConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                freelancerId,
                updatedDate
            )
            val resUpdateFailMap = managerFailUpdate.produce(updateConsultingDate)
            val failUpdate =
                resUpdateFailMap[updateConsultingDate.eventId]?.first() ?: fail("Success event not found")
            failUpdate.shouldBeInstanceOf<RequestFailed>()
            failUpdate.message.shouldBe(RequestFailedMessages.noUpdateToCloseReservation)
        }
        "produce event for agenda, member" - {
            val consultingMap = consultingManager.produce(createValidConsultingReservation)
            val createRes = consultingMap[createValidConsultingReservation.eventId]?.first() ?: fail("Success event not found")
            createRes.shouldBeInstanceOf<RequestSucceeded>()

            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            ag.retrieveConsultingReservation().first().freelancerId.freelancerId.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingMap)

            val updateConsultingDate = UpdateConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                createValidConsultingReservation.freelancer,
                updatedDate
            )
            val requestConsultingMap = manager.produce(updateConsultingDate)
            val updateEventMap = requestConsultingMap[updateConsultingDate.eventId]?.first() ?: fail("Success event not found")
            updateEventMap.shouldBeInstanceOf<RequestSucceeded>()

            val reservationEventMap = requestConsultingMap[resId] ?: fail("Reservation events not found")
            reservationEventMap.shouldHaveSingleElement { event -> event is ConsultingReservationUpdateFreelancer }
            reservationEventMap.shouldHaveSingleElement { event -> event is ConsultingReservationUpdateDate }
            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(updateConsultingDate)
            val failUpdate = resOldMemberMap[updateConsultingDate.eventId]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
            failUpdate.shouldBeInstanceOf<RequestFailed>()
        }
    }
    "A DeleteConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val deleteInvalidConsultingRequest = DeleteConsultingReservationRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                member.id
            )
            val failingRequestDueToRequestNotFound = consultingManager
                .produce(deleteInvalidConsultingRequest)[deleteInvalidConsultingRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailed
            failEvent.requestId.shouldBe(deleteInvalidConsultingRequest.eventId)
            failEvent.message.shouldBe(RequestFailedMessages.reservationNotFound)
        }
        "produce an error if member not found" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = consultingManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            ag.retrieveConsultingReservation().first().freelancerId.freelancerId.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id

            val deleteInvalidMemberConsultingRequest = DeleteConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)
            val failingRequestDueToRequestNotFound = manager
                .produce(deleteInvalidMemberConsultingRequest)[deleteInvalidMemberConsultingRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailed
            failEvent.requestId.shouldBe(deleteInvalidMemberConsultingRequest.eventId)
            failEvent.message.shouldBe(RequestFailedMessages.memberNotFound)

            val createValidConsultingReservationMember1 = CreateConsultingReservationRequest(
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

            val deleteWrongMemberConsultingRequest = DeleteConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val failingRequestDueToWrongMember = managerWith2Reservations
                .produce(deleteWrongMemberConsultingRequest)[deleteWrongMemberConsultingRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEventWrongMember = failingRequestDueToWrongMember as RequestFailed
            failEventWrongMember.requestId.shouldBe(deleteWrongMemberConsultingRequest.eventId)
            failEventWrongMember.message.shouldBe(RequestFailedMessages.wrongMember)
        }
        "produce event for agenda and member" - {
            val createMap = consultingManager.produce(createValidConsultingReservation)
            createMap[createValidConsultingReservation.eventId]?.first() ?: fail("Success event not found")
            val manager = ConsultingReservationManager(agendaId, ledgerId, createMap)

            val agendaProjection = AgendaProjection(agendaId)
            val agenda = createMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            agenda.retrieveConsultingReservation().first().freelancerId.freelancerId.shouldBe(freelancerId)
            val resId = agenda.retrieveConsultingReservation().first().id

            val deleteConsulting = DeleteConsultingReservationRequest(
                UUID.randomUUID(),
                resId,
                createValidConsultingReservation.memberId
            )

            val requestConsultingMap = manager.produce(deleteConsulting)
            requestConsultingMap[deleteConsulting.eventId]?.first() ?: fail("Success event not found")

            val agendaList = requestConsultingMap[agendaId] ?: fail("Agenda events not found")
            agendaList.first().shouldBeInstanceOf<AgendaDeleteConsultingReservation>()

            val memberList = requestConsultingMap[member.id] ?: fail("Member events not found")
            memberList.first().shouldBeInstanceOf<MemberDeleteConsultingReservation>()
            val consultingFullManager = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val resOldMemberMap = consultingFullManager.produce(deleteConsulting)
            resOldMemberMap[deleteConsulting.eventId]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
        }
    }
})
