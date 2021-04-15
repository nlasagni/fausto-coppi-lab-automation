/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.assertions.fail
import io.kotest.core.spec.style.FreeSpec
import io.kotest.fp.success
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
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
import java.util.Date
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

class ConsultingReservationManagerTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    val ledgerId = UUID.randomUUID()
    val consultingManager = ConsultingReservationManager(agendaId, ledgerId, mapOf())
    val calendar = Calendar.getInstance()
    val year = 2023
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

    fun checkFailingMessageEvent(event: Event, producer: Producer, failingMessage: String) {
        val failingCloseRequest = producer
            .produce(event)[event.eventId]?.first()
            ?: fail("Error in request")
        val failEvent = failingCloseRequest as RequestFailed
        failEvent.requestId.shouldBe(event.eventId)
        failEvent.message.shouldBe(failingMessage)
    }

    fun checkFailUpdate(map: Map<UUID, List<Event>>, id: UUID, failingMessage: String) {
        val failUpdate = map[id]?.first() ?: fail("Success event not found")
        failUpdate.shouldBeInstanceOf<RequestFailed>()
        failUpdate.requestId.shouldBe(id)
        failUpdate.message.shouldBe(failingMessage)
    }

    fun computeAgenda(events: Map<UUID, List<Event>>): Agenda {
        val agendaProjection = AgendaProjection(agendaId)
        return events[agendaId]?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
            ?: fail("Reservation not found into the agenda")
    }

    fun mergeMaps(map1: Map<UUID, List<Event>>, map2: Map<UUID, List<Event>>): Map<UUID, List<Event>> {
        return map1.entries.fold(map2) {
            map, entries ->
            val list = map.getOrDefault(entries.key, listOf()) + entries.value
            map + (entries.key to list)
        }
    }

    fun createConsulting(freelancer: UUID, date: Date, name: String, surname: String, memberId: UUID):
        CreateConsultingReservationRequest {
            return CreateConsultingReservationRequest(UUID.randomUUID(), freelancer, date, name, surname, memberId)
        }

    fun updateConsulting(resId: UUID, freelancer: UUID, date: Date): UpdateConsultingReservationRequest {
        return UpdateConsultingReservationRequest(UUID.randomUUID(), resId, freelancer, date)
    }

    fun closeConsulting(resId: UUID, member: UUID): CloseConsultingReservationRequest {
        return CloseConsultingReservationRequest(UUID.randomUUID(), resId, member)
    }

    fun deleteConsulting(resId: UUID, member: UUID): DeleteConsultingReservationRequest {
        return DeleteConsultingReservationRequest(UUID.randomUUID(), resId, member)
    }

    val createValidConsultingReservation =
        createConsulting(freelancerId, validDate, member.firstName, member.lastName, member.id)

    "A CloseConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce an error if reservation not found" - {
            val closeInvalidConsulting = closeConsulting(UUID.randomUUID(), member.id)
            checkFailingMessageEvent(
                closeInvalidConsulting,
                consultingManager,
                RequestFailedMessages.reservationNotFound
            )
        }
        "produce error if close an has invalid member or member not found" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)
            val ag = computeAgenda(consultingManagerMap)
            ag.retrieveConsultingReservation().first().freelancerId.value.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id
            val closeInvalidMemberConsultingRequest = closeConsulting(resId, member1.id)
            val managerWithReservation = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)
            checkFailingMessageEvent(
                closeInvalidMemberConsultingRequest,
                managerWithReservation,
                RequestFailedMessages.memberNotFound
            )
            val createValidConsultingReservationMember1 =
                createConsulting(freelancerId, validDate, member1.firstName, member1.lastName, member1.id)
            val mapWithAnotherReservations = managerWithReservation.produce(createValidConsultingReservationMember1)
            val mapWith2Reservation = mergeMaps(mapWithAnotherReservations, consultingManagerMap)
            val managerWith2Reservations = ConsultingReservationManager(agendaId, ledgerId, mapWith2Reservation)
            val closeWrongMemberConsultingRequest = closeConsulting(resId, member1.id)
            checkFailingMessageEvent(
                closeWrongMemberConsultingRequest,
                managerWith2Reservations,
                RequestFailedMessages.wrongMember
            )
        }
        "produce event for agenda and member" - {
            val createMap = consultingManager.produce(createValidConsultingReservation)
            val createRes = createMap[createValidConsultingReservation.eventId]?.first() ?: fail("Success event not found")
            createRes.shouldBeInstanceOf<RequestSucceeded>()
            val agenda = computeAgenda(createMap)
            agenda.retrieveConsultingReservation().first().shouldBeInstanceOf<OpenConsultingReservation>()
            val resId = agenda.retrieveConsultingReservation().first().id
            val manager = ConsultingReservationManager(agendaId, ledgerId, createMap)
            val closeConsulting = closeConsulting(resId, createValidConsultingReservation.memberId)
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
            val createInvalidFreelancerConsulting =
                createConsulting(invalidFreelancerId, validDate, member.firstName, member.firstName, member.id)
            checkFailingMessageEvent(
                createInvalidFreelancerConsulting,
                consultingManager,
                RequestFailedMessages.emptyConsultingFreelancer
            )
            val createInvalidDateConsulting =
                createConsulting(freelancerId, invalidDate, member.firstName, member.firstName, member.id)
            checkFailingMessageEvent(
                createInvalidDateConsulting,
                consultingManager,
                RequestFailedMessages.pastDateInReservation
            )
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
            val createSecondValidConsultingReservation =
                createConsulting(freelancerId, validDate, member.firstName, member.lastName, member.id)
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
            checkFailingMessageEvent(
                updateInvalidConsultingRequest,
                consultingManager,
                RequestFailedMessages.reservationNotFound
            )
        }
        "produce an error if are present empty parameters" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)
            val ag = computeAgenda(consultingManagerMap)
            ag.retrieveConsultingReservation().first().freelancerId.value.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)
            val updateInvalidEmptyFreelancer = updateConsulting(resId, invalidFreelancerId, updatedDate)
            val requestFailFreelancerConsultingMap = manager.produce(updateInvalidEmptyFreelancer)
            checkFailUpdate(
                requestFailFreelancerConsultingMap,
                updateInvalidEmptyFreelancer.eventId,
                RequestFailedMessages.emptyConsultingFreelancer
            )
            val updateInvalidConsultingDueToPastDateRequest = updateConsulting(resId, freelancerId, invalidDate)
            val requestFailDateConsultingMap = manager.produce(updateInvalidConsultingDueToPastDateRequest)
            checkFailUpdate(
                requestFailDateConsultingMap,
                updateInvalidConsultingDueToPastDateRequest.eventId,
                RequestFailedMessages.pastDateInReservation
            )
        }
        "produce an error if a closeConsultingReservation is updated" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)
            val ag = computeAgenda(consultingManagerMap)
            ag.retrieveConsultingReservation().first().freelancerId.value.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)
            val closeConsulting = closeConsulting(resId, createValidConsultingReservation.memberId)
            val requestConsultingMap = manager.produce(closeConsulting)
            val managerFailUpdate = ConsultingReservationManager(agendaId, ledgerId, requestConsultingMap)
            val updateDate = updateConsulting(resId, freelancerId, updatedDate)
            val resUpdateFailMap = managerFailUpdate.produce(updateDate)
            checkFailUpdate(resUpdateFailMap, updateDate.eventId, RequestFailedMessages.noUpdateToCloseReservation)
        }
        "produce event for agenda and member" - {
            val consultingMap = consultingManager.produce(createValidConsultingReservation)
            val createRes = consultingMap[createValidConsultingReservation.eventId]?.first() ?: fail("Success event not found")
            createRes.shouldBeInstanceOf<RequestSucceeded>()
            val ag = computeAgenda(consultingMap)
            ag.retrieveConsultingReservation().first().freelancerId.value.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingMap)
            val updateConsultingDate = updateConsulting(resId, createValidConsultingReservation.freelancer, updatedDate)
            val requestConsultingMap = manager.produce(updateConsultingDate)
            val updateEventMap = requestConsultingMap[updateConsultingDate.eventId]?.first() ?: fail("Success event not found")
            updateEventMap.shouldBeInstanceOf<RequestSucceeded>()
            val reservationEventMap = requestConsultingMap[resId] ?: fail("Reservation events not found")
            reservationEventMap.shouldHaveSingleElement { event -> event is ConsultingReservationUpdateFreelancer }
            reservationEventMap.shouldHaveSingleElement { event -> event is ConsultingReservationUpdateDate }
        }
    }
    "A DeleteConsultingReservationEvent occurring in ConsultingReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val deleteInvalidConsultingRequest = deleteConsulting(UUID.randomUUID(), member.id)
            checkFailingMessageEvent(
                deleteInvalidConsultingRequest,
                consultingManager,
                RequestFailedMessages.reservationNotFound
            )
        }
        "produce an error if member not found" - {
            val consultingManagerMap = consultingManager.produce(createValidConsultingReservation)
            val ag = computeAgenda(consultingManagerMap)
            ag.retrieveConsultingReservation().first().freelancerId.value.shouldBe(freelancerId)
            val resId = ag.retrieveConsultingReservation().first().id
            val deleteInvalidMemberConsultingRequest = deleteConsulting(resId, member1.id)
            val manager = ConsultingReservationManager(agendaId, ledgerId, consultingManagerMap)
            checkFailingMessageEvent(
                deleteInvalidMemberConsultingRequest,
                manager,
                RequestFailedMessages.memberNotFound
            )
            val createValidConsultingReservationMember1 = CreateConsultingReservationRequest(
                UUID.randomUUID(),
                freelancerId,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val mapWithAnotherReservations = manager.produce(createValidConsultingReservationMember1)
            val mapWith2Reservation = mergeMaps(mapWithAnotherReservations, consultingManagerMap)
            val managerWith2Reservations = ConsultingReservationManager(agendaId, ledgerId, mapWith2Reservation)
            val deleteWrongMemberConsultingRequest = deleteConsulting(resId, member1.id)
            checkFailingMessageEvent(
                deleteWrongMemberConsultingRequest,
                managerWith2Reservations,
                RequestFailedMessages.wrongMember
            )
        }
        "produce event for agenda and member" - {
            val createMap = consultingManager.produce(createValidConsultingReservation)
            createMap[createValidConsultingReservation.eventId]?.first() ?: fail("Success event not found")
            val manager = ConsultingReservationManager(agendaId, ledgerId, createMap)
            val agenda = computeAgenda(createMap)
            agenda.retrieveConsultingReservation().first().freelancerId.value.shouldBe(freelancerId)
            val resId = agenda.retrieveConsultingReservation().first().id
            val deleteConsulting = deleteConsulting(resId, createValidConsultingReservation.memberId)
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
