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
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMember
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDate
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateAim
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateDate
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservationRequest
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailed
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceeded
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import java.util.Calendar
import java.util.Date
import java.util.UUID

class WorkoutReservationManagerTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    val ledgerId = UUID.randomUUID()
    val workoutManager = WorkoutReservationManager(agendaId, ledgerId)
    val calendar = Calendar.getInstance()
    val validYear = 2023
    val invalidYear = 2020
    val feb = Calendar.FEBRUARY
    val day = 25
    calendar.set(validYear, feb, day)
    val validDate = calendar.time
    calendar.set(validYear, Calendar.MARCH, day)
    val updateDate = calendar.time
    calendar.set(invalidYear, feb, day)
    val invalidDate = calendar.time
    val member1 = Member("Mario", "Bianchi", UUID.randomUUID())
    val validAim = "Injury recovery"
    val invalidAim = ""
    val updateAim = "Strengthening"
    var member = Member("Mario", "Rossi", UUID.randomUUID())

    fun checkFailingMessageEvent(event: Event, producer: Producer, failingMessage: String) {
        val failingCloseRequest = producer
            .produce(event)[event.eventId]?.first()
            ?: fail("Error in request")
        val failEvent = failingCloseRequest as RequestFailed
        failEvent.requestId.shouldBe(event.eventId)
        failEvent.message.shouldBe(failingMessage)
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

    fun checkFailUpdate(map: Map<UUID, List<Event>>, id: UUID, failingMessage: String) {
        val failUpdate = map[id]?.first() ?: fail("Success event not found")
        failUpdate.shouldBeInstanceOf<RequestFailed>()
        failUpdate.requestId.shouldBe(id)
        failUpdate.message.shouldBe(failingMessage)
    }

    fun createWorkout(aim: String, date: Date, name: String, surname: String, memberId: UUID):
        CreateWorkoutReservationRequest {
            return CreateWorkoutReservationRequest(UUID.randomUUID(), aim, date, name, surname, memberId)
        }

    fun updateWorkout(resId: UUID, aim: String, date: Date): UpdateWorkoutReservationRequest {
        return UpdateWorkoutReservationRequest(UUID.randomUUID(), resId, aim, date)
    }

    fun closeWorkout(resId: UUID, member: UUID): CloseWorkoutReservationRequest {
        return CloseWorkoutReservationRequest(UUID.randomUUID(), resId, member)
    }

    fun deleteWorkout(resId: UUID, member: UUID): DeleteWorkoutReservationRequest {
        return DeleteWorkoutReservationRequest(UUID.randomUUID(), resId, member)
    }

    val createValidWorkoutReservation =
        createWorkout(validAim, validDate, member.firstName, member.lastName, member.id)

    "When CloseWorkoutReservationEvent occurring in WorkoutReservationManager should" - {
        "produce an error if reservation not found" - {
            val closeInvalidWorkout = closeWorkout(UUID.randomUUID(), member.id)
            checkFailingMessageEvent(closeInvalidWorkout, workoutManager, RequestFailedMessages.reservationNotFound)
        }
        "produce error if close an has invalid member or member not found" - {
            val workoutManagerMap = workoutManager.produce(createValidWorkoutReservation)
            val ag = computeAgenda(workoutManagerMap)
            ag.retrieveWorkoutReservation().first().aim.value.shouldBe(validAim)
            val resId = ag.retrieveWorkoutReservation().first().id
            val closeInvalidMemberWorkoutRequest = closeWorkout(resId, member1.id)
            val managerWithReservation = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)
            checkFailingMessageEvent(
                closeInvalidMemberWorkoutRequest,
                managerWithReservation,
                RequestFailedMessages.memberNotFound
            )
            val createValidWorkoutReservationMember1 =
                createWorkout(validAim, validDate, member1.firstName, member1.lastName, member1.id)
            val mapWithAnotherReservations = managerWithReservation.produce(createValidWorkoutReservationMember1)
            val mapWith2Reservation = mergeMaps(mapWithAnotherReservations, workoutManagerMap)
            val managerWith2Reservations = WorkoutReservationManager(agendaId, ledgerId, mapWith2Reservation)
            val closeWrongMemberWorkoutRequest = closeWorkout(resId, member1.id)
            checkFailingMessageEvent(
                closeWrongMemberWorkoutRequest,
                managerWith2Reservations,
                RequestFailedMessages.wrongMember
            )
        }
        "produce event for agenda and member" - {
            val createMap = workoutManager.produce(createValidWorkoutReservation)
            val createRes = createMap[createValidWorkoutReservation.eventId]?.first() ?: fail("Success event not found")
            createRes.shouldBeInstanceOf<RequestSucceeded>()
            val agenda = computeAgenda(createMap)
            agenda.retrieveWorkoutReservation().first().shouldBeInstanceOf<OpenWorkoutReservation>()
            val resId = agenda.retrieveWorkoutReservation().first().id
            val manager = WorkoutReservationManager(agendaId, ledgerId, createMap)
            val closeWorkout = closeWorkout(resId, createValidWorkoutReservation.memberId)
            val requestWorkoutMap = manager.produce(closeWorkout)
            requestWorkoutMap[closeWorkout.eventId]?.first() ?: fail("Success event not found")
            val agendaDeleteFromList = requestWorkoutMap[agendaId] ?: fail("Agenda events not found")
            agendaDeleteFromList.shouldHaveSingleElement { event -> event is AgendaDeleteWorkoutReservation }
            agendaDeleteFromList.shouldHaveSingleElement { event -> event is AgendaAddWorkoutReservation }
            val memberList = requestWorkoutMap[member.id] ?: fail("Member events not found")
            memberList.shouldHaveSingleElement { event -> event is MemberDeleteWorkoutReservation }
            memberList.shouldHaveSingleElement { event -> event is MemberAddWorkoutReservation }
            val closeResult = requestWorkoutMap[closeWorkout.eventId]?.first() ?: fail("Success event not found")
            closeResult.shouldBeInstanceOf<RequestSucceeded>()

            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)
            val resOldMemberMap = workoutFullManager.produce(closeWorkout)
            val closeFail = resOldMemberMap[closeWorkout.eventId]?.first() ?: fail("Success event not found")
            closeFail.shouldBeInstanceOf<RequestFailed>()
            closeFail.message.shouldBe(RequestFailedMessages.alreadyCloseReservation)
        }
    }
    "When a CreateWorkoutReservationEvent occurs the WorkoutReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val invalidReservationAimEvent = CreateWorkoutReservationRequest(
                UUID.randomUUID(),
                invalidAim,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            checkFailingMessageEvent(
                invalidReservationAimEvent,
                workoutManager,
                RequestFailedMessages.emptyWorkoutAim
            )
            val invalidReservationDateEvent = CreateWorkoutReservationRequest(
                UUID.randomUUID(),
                validAim,
                invalidDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            checkFailingMessageEvent(
                invalidReservationDateEvent,
                workoutManager,
                RequestFailedMessages.pastDateInReservation
            )
        }
        "produce events for agenda, member and ledger" - {
            val resNewMemberMap = workoutManager.produce(createValidWorkoutReservation)
            resNewMemberMap[createValidWorkoutReservation.eventId]?.first() ?: fail("Success event not found")
            val agendaList = resNewMemberMap[agendaId] ?: fail("Agenda events not found")
            agendaList.first().shouldBeInstanceOf<AgendaAddWorkoutReservation>()
            val ledgerList = resNewMemberMap[ledgerId] ?: fail("Ledger events not found")
            ledgerList.first().shouldBeInstanceOf<LedgerAddMember>()
            val memberList = resNewMemberMap[member.id] ?: fail("Member events not found")
            memberList.first().shouldBeInstanceOf<MemberAddWorkoutReservation>()
            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, resNewMemberMap)

            val createSecondValidWorkoutReservation = CreateWorkoutReservationRequest(
                UUID.randomUUID(),
                validAim,
                validDate,
                member.firstName,
                member.lastName,
                member.id
            )
            val mapWithExistingMember = workoutFullManager.produce(createSecondValidWorkoutReservation)
            mapWithExistingMember[ledgerId]?.first() ?: success()
        }
    }
    "An UpdateWorkoutReservationEvent occurring in WorkoutReservationManager should" - {
        "produce empty map if event is not valid" - {
            val invalidEvent = ConsultingReservationUpdateDate(UUID.randomUUID(), validDate)
            val emptyMap = workoutManager.produce(invalidEvent)
            emptyMap.shouldBeEmpty()
        }
        "produce an error if parameters are not valid" - {
            val updateInvalidWorkoutRequest = updateWorkout(UUID.randomUUID(), validAim, updateDate)
            checkFailingMessageEvent(
                updateInvalidWorkoutRequest,
                workoutManager,
                RequestFailedMessages.reservationNotFound
            )
        }
        "produce an error if are present empty parameters" - {
            val workoutManagerMap = workoutManager.produce(createValidWorkoutReservation)
            val ag = computeAgenda(workoutManagerMap)
            ag.retrieveWorkoutReservation().first().aim.value.shouldBe(validAim)
            val resId = ag.retrieveWorkoutReservation().first().id
            val manager = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)
            val updateInvalidWorkoutDueToEmptyAimRequest = updateWorkout(resId, invalidAim, updateDate)
            val requestFailAimWorkoutMap = manager.produce(updateInvalidWorkoutDueToEmptyAimRequest)
            checkFailUpdate(
                requestFailAimWorkoutMap,
                updateInvalidWorkoutDueToEmptyAimRequest.eventId,
                RequestFailedMessages.emptyWorkoutAim
            )
            val updateInvalidWorkoutDueToPastDateRequest = updateWorkout(resId, validAim, invalidDate)
            val requestFailDateWorkoutMap = manager.produce(updateInvalidWorkoutDueToPastDateRequest)
            checkFailUpdate(
                requestFailDateWorkoutMap,
                updateInvalidWorkoutDueToPastDateRequest.eventId,
                RequestFailedMessages.pastDateInReservation
            )
        }
        "produce an error if a closeWorkoutReservation is updated" - {
            val workoutManagerMap = workoutManager.produce(createValidWorkoutReservation)
            val ag = computeAgenda(workoutManagerMap)
            ag.retrieveWorkoutReservation().first().aim.value.shouldBe(validAim)
            val resId = ag.retrieveWorkoutReservation().first().id
            val manager = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)
            val closeWorkout = closeWorkout(resId, createValidWorkoutReservation.memberId)
            val requestWorkoutMap = manager.produce(closeWorkout)
            val managerFailUpdate = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)
            val updateWorkoutDate = updateWorkout(resId, updateAim, updateDate)
            val resUpdateFailMap = managerFailUpdate.produce(updateWorkoutDate)
            checkFailUpdate(
                resUpdateFailMap,
                updateWorkoutDate.eventId,
                RequestFailedMessages.noUpdateToCloseReservation
            )
        }
        "produce event for agenda and member" - {
            val workoutMap = workoutManager.produce(createValidWorkoutReservation)
            val createRes = workoutMap[createValidWorkoutReservation.eventId]?.first() ?: fail("Success event not found")
            createRes.shouldBeInstanceOf<RequestSucceeded>()
            val ag = computeAgenda(workoutMap)
            ag.retrieveWorkoutReservation().first().aim.value.shouldBe(validAim)
            val resId = ag.retrieveWorkoutReservation().first().id
            val manager = WorkoutReservationManager(agendaId, ledgerId, workoutMap)
            val updateWorkoutDate = updateWorkout(resId, updateAim, updateDate)
            val requestWorkoutMap = manager.produce(updateWorkoutDate)
            val updateEventMap = requestWorkoutMap[updateWorkoutDate.eventId]?.first() ?: fail("Success event not found")
            updateEventMap.shouldBeInstanceOf<RequestSucceeded>()
            val reservationEventMap = requestWorkoutMap[resId] ?: fail("Reservation events not found")
            reservationEventMap.shouldHaveSingleElement { event -> event is WorkoutReservationUpdateAim }
            reservationEventMap.shouldHaveSingleElement { event -> event is WorkoutReservationUpdateDate }
        }
    }
    "When a DeleteWorkoutReservationEvent occurs in the WorkoutReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val deleteInvalidWorkoutRequest = deleteWorkout(UUID.randomUUID(), member.id)
            checkFailingMessageEvent(
                deleteInvalidWorkoutRequest,
                workoutManager,
                RequestFailedMessages.reservationNotFound
            )
        }
        "produce an error if member not found" - {
            val workoutManagerMap = workoutManager.produce(createValidWorkoutReservation)
            val ag = computeAgenda(workoutManagerMap)
            ag.retrieveWorkoutReservation().first().aim.value.shouldBe(validAim)
            val resId = ag.retrieveWorkoutReservation().first().id
            val deleteInvalidMemberWorkoutRequest = deleteWorkout(resId, member1.id)
            val managerWithReservation = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)
            checkFailingMessageEvent(
                deleteInvalidMemberWorkoutRequest,
                managerWithReservation,
                RequestFailedMessages.memberNotFound
            )
            val createValidWorkoutReservationMember1 =
                createWorkout(validAim, validDate, member1.firstName, member1.lastName, member1.id)
            val mapWithAnotherReservations = managerWithReservation.produce(createValidWorkoutReservationMember1)
            val mapWith2Reservation = mergeMaps(mapWithAnotherReservations, workoutManagerMap)
            val managerWith2Reservations = WorkoutReservationManager(agendaId, ledgerId, mapWith2Reservation)
            val deleteWrongMemberWorkoutRequest = deleteWorkout(resId, member1.id)
            checkFailingMessageEvent(
                deleteWrongMemberWorkoutRequest,
                managerWith2Reservations,
                RequestFailedMessages.wrongMember
            )
        }
        "produce event for agenda and member" - {
            val createMap = workoutManager.produce(createValidWorkoutReservation)
            createMap[createValidWorkoutReservation.eventId]?.first() ?: fail("Success event not found")
            val manager = WorkoutReservationManager(agendaId, ledgerId, createMap)
            val agenda = computeAgenda(createMap)
            agenda.retrieveWorkoutReservation().first().aim.value.shouldBe(validAim)
            val resId = agenda.retrieveWorkoutReservation().first().id
            val deleteWorkout = deleteWorkout(resId, createValidWorkoutReservation.memberId)
            val requestWorkoutMap = manager.produce(deleteWorkout)
            requestWorkoutMap[deleteWorkout.eventId]?.first() ?: fail("Success event not found")
            val agendaList = requestWorkoutMap[agendaId] ?: fail("Agenda events not found")
            agendaList.first().shouldBeInstanceOf<AgendaDeleteWorkoutReservation>()
            val memberList = requestWorkoutMap[member.id] ?: fail("Member events not found")
            memberList.first().shouldBeInstanceOf<MemberDeleteWorkoutReservation>()
            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)
            val resOldMemberMap = workoutFullManager.produce(deleteWorkout)
            resOldMemberMap[deleteWorkout.eventId]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
        }
    }
})
