package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.assertions.fail
import io.kotest.core.spec.style.FreeSpec
import io.kotest.fp.success
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
import java.util.UUID

class WorkoutReservationManagerTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    val ledgerId = UUID.randomUUID()
    val workoutManager = WorkoutReservationManager(agendaId, ledgerId)
    val calendar = Calendar.getInstance()
    val validYear = 2022
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

    val createValidWorkoutReservation = CreateWorkoutReservationRequest(
        UUID.randomUUID(),
        validAim,
        validDate,
        member.firstName,
        member.lastName,
        member.id
    )

    "When CloseWorkoutReservationEvent occurring in WorkoutReservationManager should" - {
        "produce an error if reservation not found" - {
            val closeInvalidWorkout = CloseWorkoutReservationRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                member.id
            )

            val failingCloseRequest = workoutManager
                .produce(closeInvalidWorkout)[closeInvalidWorkout.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingCloseRequest as RequestFailed
            assert(failEvent.requestId == closeInvalidWorkout.eventId)
            assert(failEvent.message == RequestFailedMessages.reservationNotFound)
        }
        "produce error if close an has invalid member or member not found" - {
            val workoutManagerMap = workoutManager.produce(createValidWorkoutReservation)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = workoutManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveWorkoutReservation().first().aim == validAim)
            val resId = ag.retrieveWorkoutReservation().first().id

            val closeInvalidMemberWorkoutRequest = CloseWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val managerWithReservation = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)
            val failingRequestDueToRequestNotFound = managerWithReservation
                .produce(closeInvalidMemberWorkoutRequest)[closeInvalidMemberWorkoutRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailed
            assert(failEvent.requestId == closeInvalidMemberWorkoutRequest.eventId)
            assert(failEvent.message == RequestFailedMessages.memberNotFound)

            val createValidWorkoutReservationMember1 = CreateWorkoutReservationRequest(
                UUID.randomUUID(),
                validAim,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val mapWithAnotherReservations = managerWithReservation.produce(createValidWorkoutReservationMember1)
            val mapWith2Reservation = mapWithAnotherReservations.entries.fold(workoutManagerMap) {
                map, entries ->
                val list = map.getOrDefault(entries.key, listOf()) + entries.value
                map + (entries.key to list)
            }
            val managerWith2Reservations = WorkoutReservationManager(agendaId, ledgerId, mapWith2Reservation)

            val closeWrongMemberWorkoutRequest = CloseWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val failingRequestDueToWrongMember = managerWith2Reservations
                .produce(closeWrongMemberWorkoutRequest)[closeWrongMemberWorkoutRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEventWrongMember = failingRequestDueToWrongMember as RequestFailed
            assert(failEventWrongMember.requestId == closeWrongMemberWorkoutRequest.eventId)
            assert(failEventWrongMember.message == RequestFailedMessages.wrongMember)
        }
        "produce event for agenda, member" - {
            val createMap = workoutManager.produce(createValidWorkoutReservation)
            val createRes = createMap[createValidWorkoutReservation.eventId]?.first() ?: fail("Success event not found")
            assert(createRes is RequestSucceeded)

            val agendaProjection = AgendaProjection(agendaId)
            val agenda = createMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(agenda.retrieveWorkoutReservation().first() is OpenWorkoutReservation)
            val resId = agenda.retrieveWorkoutReservation().first().id

            val manager = WorkoutReservationManager(agendaId, ledgerId, createMap)
            val closeWorkout = CloseWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                createValidWorkoutReservation.memberId
            )
            val requestWorkoutMap = manager.produce(closeWorkout)
            requestWorkoutMap[closeWorkout.eventId]?.first() ?: fail("Success event not found")

            val agendaDeleteFromList = requestWorkoutMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaDeleteFromList.any { event -> event is AgendaDeleteWorkoutReservation })
            assert(agendaDeleteFromList.any { event -> event is AgendaAddWorkoutReservation })

            val memberList = requestWorkoutMap[member.id] ?: fail("Member events not found")
            assert(memberList.any { event -> event is MemberDeleteWorkoutReservation })
            assert(memberList.any { event -> event is MemberAddWorkoutReservation })

            val closeResult = requestWorkoutMap[closeWorkout.eventId]?.first() ?: fail("Success event not found")
            assert(closeResult is RequestSucceeded)

            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)
            val resOldMemberMap = workoutFullManager.produce(closeWorkout)
            val closeFail = resOldMemberMap[closeWorkout.eventId]?.first() ?: fail("Success event not found")
            assert(closeFail is RequestFailed)
            val failMessage = closeFail as RequestFailed
            assert(failMessage.message == RequestFailedMessages.alreadyCloseReservation)
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
            val invalidReservationDateEvent = CreateWorkoutReservationRequest(
                UUID.randomUUID(),
                validAim,
                invalidDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val resInvAim = workoutManager
                .produce(invalidReservationAimEvent)[invalidReservationAimEvent.eventId]?.first()
                ?: fail("Error event not found")
            assert(resInvAim is RequestFailed)
            val failAimEvent = resInvAim as RequestFailed
            assert(failAimEvent.requestId == invalidReservationAimEvent.eventId)
            assert(failAimEvent.message == RequestFailedMessages.emptyWorkoutAim)
            val resInvDate = workoutManager
                .produce(invalidReservationDateEvent)[invalidReservationDateEvent.eventId]?.first()
                ?: fail("Error event not found")
            assert(resInvDate is RequestFailed)
            val failDateEvent = resInvDate as RequestFailed
            assert(failDateEvent.requestId == invalidReservationDateEvent.eventId)
            assert(failDateEvent.message == RequestFailedMessages.pastDateInReservation)
        }
        "produce events for agenda, member and ledger" - {
            val resNewMemberMap = workoutManager
                .produce(createValidWorkoutReservation)
            resNewMemberMap[createValidWorkoutReservation.eventId]?.first() ?: fail("Success event not found")
            val agendaList = resNewMemberMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaList.first() is AgendaAddWorkoutReservation)
            val ledgerList = resNewMemberMap[ledgerId] ?: fail("Ledger events not found")
            assert(ledgerList.first() is LedgerAddMember)
            val memberList = resNewMemberMap[member.id] ?: fail("Member events not found")
            assert(memberList.first() is MemberAddWorkoutReservation)
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
            assert(emptyMap.isEmpty())
        }
        "produce an error if parameters are not valid" - {
            val updateInvalidWorkoutRequest = UpdateWorkoutReservationRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                validAim,
                updateDate
            )
            val failingRequestDueToRequestNotFound = workoutManager
                .produce(updateInvalidWorkoutRequest)[updateInvalidWorkoutRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailed
            assert(failEvent.requestId == updateInvalidWorkoutRequest.eventId)
            assert(failEvent.message == RequestFailedMessages.reservationNotFound)
        }
        "produce an error if are present empty parameters" - {
            val workoutManagerMap = workoutManager.produce(createValidWorkoutReservation)
            val agendaProjection = AgendaProjection(agendaId)
            val ag = workoutManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveWorkoutReservation().first().aim == validAim)
            val resId = ag.retrieveWorkoutReservation().first().id
            val manager = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)

            val updateInvalidWorkoutDueToEmptyAimRequest = UpdateWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                invalidAim,
                updateDate
            )
            val requestFailAimWorkoutMap = manager.produce(updateInvalidWorkoutDueToEmptyAimRequest)

            val failUpdate =
                requestFailAimWorkoutMap[updateInvalidWorkoutDueToEmptyAimRequest.eventId]
                    ?.first() ?: fail("Success event not found")
            assert(failUpdate is RequestFailed)
            val failEvent = failUpdate as RequestFailed
            assert(failEvent.requestId == updateInvalidWorkoutDueToEmptyAimRequest.eventId)
            assert(failEvent.message == RequestFailedMessages.emptyWorkoutAim)

            val updateInvalidWorkoutDueToPastDateRequest = UpdateWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                validAim,
                invalidDate
            )
            val requestFailDateWorkoutMap = manager.produce(updateInvalidWorkoutDueToPastDateRequest)

            val failUpdatePastDate =
                requestFailDateWorkoutMap[updateInvalidWorkoutDueToPastDateRequest.eventId]
                    ?.first() ?: fail("Success event not found")
            assert(failUpdatePastDate is RequestFailed)
            val failEventPastDate = failUpdatePastDate as RequestFailed
            assert(failEventPastDate.requestId == updateInvalidWorkoutDueToPastDateRequest.eventId)
            assert(failEventPastDate.message == RequestFailedMessages.pastDateInReservation)
        }
        "produce an error if a closeConsultingReservation is updated" - {
            val workoutManagerMap = workoutManager.produce(createValidWorkoutReservation)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = workoutManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveWorkoutReservation().first().aim == validAim)
            val resId = ag.retrieveWorkoutReservation().first().id

            val manager = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)

            val closeWorkout = CloseWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                createValidWorkoutReservation.memberId
            )
            val requestWorkoutMap = manager.produce(closeWorkout)

            val managerFailUpdate = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)

            val updateWorkoutDate = UpdateWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                updateAim,
                updateDate
            )
            val resUpdateFailMap = managerFailUpdate.produce(updateWorkoutDate)
            val failUpdate =
                resUpdateFailMap[updateWorkoutDate.eventId]?.first() ?: fail("Success event not found")
            assert(failUpdate is RequestFailed)
            val failMessage = failUpdate as RequestFailed
            assert(failMessage.message == RequestFailedMessages.noUpdateToCloseReservation)
        }
        "produce event for agenda, member" - {
            val workoutMap = workoutManager.produce(createValidWorkoutReservation)
            val createRes = workoutMap[createValidWorkoutReservation.eventId]?.first() ?: fail("Success event not found")
            assert(createRes is RequestSucceeded)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = workoutMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveWorkoutReservation().first().aim == validAim)
            val resId = ag.retrieveWorkoutReservation().first().id
            val manager = WorkoutReservationManager(agendaId, ledgerId, workoutMap)

            val updateWorkoutDate = UpdateWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                updateAim,
                updateDate
            )
            val requestWorkoutMap = manager.produce(updateWorkoutDate)
            val updateEventMap = requestWorkoutMap[updateWorkoutDate.eventId]?.first() ?: fail("Success event not found")
            assert(updateEventMap is RequestSucceeded)

            val reservationEventMap = requestWorkoutMap[resId] ?: fail("Reservation events not found")
            assert(reservationEventMap.any { event -> event is WorkoutReservationUpdateAim })
            assert(reservationEventMap.any { event -> event is WorkoutReservationUpdateDate })
            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)
            val resOldMemberMap = workoutFullManager.produce(updateWorkoutDate)
            val failUpdate =
                resOldMemberMap[updateWorkoutDate.eventId]?.first() ?: fail("Success event not found")
            assert(failUpdate is RequestFailed)
        }
    }
    "When a DeleteWorkoutReservationEvent occurs in the WorkoutReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val deleteInvalidWorkoutRequest = DeleteWorkoutReservationRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                member.id
            )
            val failingRequestDueToRequestNotFound = workoutManager
                .produce(deleteInvalidWorkoutRequest)[deleteInvalidWorkoutRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailed
            assert(failEvent.requestId == deleteInvalidWorkoutRequest.eventId)
            assert(failEvent.message == RequestFailedMessages.reservationNotFound)
        }
        "produce an error if member not found" - {
            val workoutManagerMap = workoutManager.produce(createValidWorkoutReservation)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = workoutManagerMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveWorkoutReservation().first().aim == validAim)
            val resId = ag.retrieveWorkoutReservation().first().id

            val deleteInvalidMemberWorkoutRequest = DeleteWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val managerWithReservation = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)
            val failingRequestDueToRequestNotFound = managerWithReservation
                .produce(deleteInvalidMemberWorkoutRequest)[deleteInvalidMemberWorkoutRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailed
            assert(failEvent.requestId == deleteInvalidMemberWorkoutRequest.eventId)
            assert(failEvent.message == RequestFailedMessages.memberNotFound)

            val createValidWorkoutReservationMember1 = CreateWorkoutReservationRequest(
                UUID.randomUUID(),
                validAim,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val mapWithAnotherReservations = managerWithReservation.produce(createValidWorkoutReservationMember1)
            val mapWith2Reservation = mapWithAnotherReservations.entries.fold(workoutManagerMap) {
                map, entries ->
                val list = map.getOrDefault(entries.key, listOf()) + entries.value
                map + (entries.key to list)
            }
            val managerWith2Reservations = WorkoutReservationManager(agendaId, ledgerId, mapWith2Reservation)

            val deleteWrongMemberWorkoutRequest = DeleteWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val failingRequestDueToWrongMember = managerWith2Reservations
                .produce(deleteWrongMemberWorkoutRequest)[deleteWrongMemberWorkoutRequest.eventId]?.first()
                ?: fail("Error in request")
            val failEventWrongMember = failingRequestDueToWrongMember as RequestFailed
            assert(failEventWrongMember.requestId == deleteWrongMemberWorkoutRequest.eventId)
            assert(failEventWrongMember.message == RequestFailedMessages.wrongMember)
        }
        "produce event for agenda and member" - {
            val createMap = workoutManager.produce(createValidWorkoutReservation)
            createMap[createValidWorkoutReservation.eventId]?.first() ?: fail("Success event not found")
            val manager = WorkoutReservationManager(agendaId, ledgerId, createMap)

            val agendaProjection = AgendaProjection(agendaId)
            val agenda = createMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(agenda.retrieveWorkoutReservation().first().aim == validAim)
            val resId = agenda.retrieveWorkoutReservation().first().id

            val deleteWorkout = DeleteWorkoutReservationRequest(
                UUID.randomUUID(),
                resId,
                createValidWorkoutReservation.memberId
            )

            val requestWorkoutMap = manager.produce(deleteWorkout)
            requestWorkoutMap[deleteWorkout.eventId]?.first() ?: fail("Success event not found")
            val agendaList = requestWorkoutMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaList.first() is AgendaDeleteWorkoutReservation)
            val memberList = requestWorkoutMap[member.id] ?: fail("Member events not found")
            assert(memberList.first() is MemberDeleteWorkoutReservation)
            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)
            val resOldMemberMap = workoutFullManager.produce(deleteWorkout)
            resOldMemberMap[deleteWorkout.eventId]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
        }
    }
})
