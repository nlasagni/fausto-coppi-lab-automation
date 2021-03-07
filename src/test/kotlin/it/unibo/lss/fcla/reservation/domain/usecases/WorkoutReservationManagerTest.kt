package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.assertions.fail
import io.kotest.core.spec.style.FreeSpec
import io.kotest.fp.success
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateAimEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestSucceededEvent
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

    val createValidWorkoutReservation = CreateWorkoutReservationEvent(
        UUID.randomUUID(),
        validAim,
        validDate,
        member.firstName,
        member.lastName,
        member.id
    )

    "When CloseWorkoutReservationEvent occurring in WorkoutReservationManager should" - {
        "produce an error if reservation not found" - {
            val closeInvalidWorkout = CloseWorkoutReservationEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                member.id
            )

            val failingCloseRequest = workoutManager
                .produce(closeInvalidWorkout)[closeInvalidWorkout.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingCloseRequest as RequestFailedEvent
            assert(failEvent.requestId == closeInvalidWorkout.id)
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

            val closeInvalidMemberWorkoutRequest = CloseWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val managerWithReservation = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)
            val failingRequestDueToRequestNotFound = managerWithReservation
                .produce(closeInvalidMemberWorkoutRequest)[closeInvalidMemberWorkoutRequest.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailedEvent
            assert(failEvent.requestId == closeInvalidMemberWorkoutRequest.id)
            assert(failEvent.message == RequestFailedMessages.memberNotFound)

            val createValidWorkoutReservationMember1 = CreateWorkoutReservationEvent(
                UUID.randomUUID(),
                validAim,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val mapWithAnotherReservations = managerWithReservation.produce(createValidWorkoutReservationMember1)
            val mapWith2Reservation = mapWithAnotherReservations.entries.fold(workoutManagerMap){
                    map,entries ->
                val list = map.getOrDefault(entries.key, listOf()) + entries.value
                map + (entries.key to list)
            }
            val managerWith2Reservations = WorkoutReservationManager(agendaId, ledgerId, mapWith2Reservation)

            val closeWrongMemberWorkoutRequest = CloseWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val failingRequestDueToWrongMember = managerWith2Reservations
                .produce(closeWrongMemberWorkoutRequest)[closeWrongMemberWorkoutRequest.id]?.first()
                ?: fail("Error in request")
            val failEventWrongMember = failingRequestDueToWrongMember as RequestFailedEvent
            assert(failEventWrongMember.requestId == closeWrongMemberWorkoutRequest.id)
            assert(failEventWrongMember.message == RequestFailedMessages.wrongMember)
        }
        "produce event for agenda, member" - {
            val createMap = workoutManager.produce(createValidWorkoutReservation)
            val createRes = createMap[createValidWorkoutReservation.id]?.first() ?: fail("Success event not found")
            assert(createRes is RequestSucceededEvent)

            val agendaProjection = AgendaProjection(agendaId)
            val agenda = createMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(agenda.retrieveWorkoutReservation().first() is OpenWorkoutReservation)
            val resId = agenda.retrieveWorkoutReservation().first().id

            val manager = WorkoutReservationManager(agendaId, ledgerId, createMap)
            val closeWorkout = CloseWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                createValidWorkoutReservation.memberId
            )
            val requestWorkoutMap = manager.produce(closeWorkout)
            requestWorkoutMap[closeWorkout.id]?.first() ?: fail("Success event not found")

            val agendaDeleteFromList = requestWorkoutMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaDeleteFromList.any { event -> event is AgendaDeleteWorkoutReservationEvent })
            assert(agendaDeleteFromList.any { event -> event is AgendaAddWorkoutReservationEvent })

            val memberList = requestWorkoutMap[member.id] ?: fail("Member events not found")
            assert(memberList.any { event -> event is MemberDeleteWorkoutReservationEvent })
            assert(memberList.any { event -> event is MemberAddWorkoutReservationEvent })

            val closeResult = requestWorkoutMap[closeWorkout.id]?.first() ?: fail("Success event not found")
            assert(closeResult is RequestSucceededEvent)

            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)
            val resOldMemberMap = workoutFullManager.produce(closeWorkout)
            val closeFail = resOldMemberMap[closeWorkout.id]?.first() ?: fail("Success event not found")
            assert(closeFail is RequestFailedEvent)
            val failMessage = closeFail as RequestFailedEvent
            assert(failMessage.message == RequestFailedMessages.alreadyCloseReservation)
        }
    }
    "When a CreateWorkoutReservationEvent occurs the WorkoutReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val invalidReservationAimEvent = CreateWorkoutReservationEvent(
                UUID.randomUUID(),
                invalidAim,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val invalidReservationDateEvent = CreateWorkoutReservationEvent(
                UUID.randomUUID(),
                validAim,
                invalidDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val resInvAim = workoutManager
                .produce(invalidReservationAimEvent)[invalidReservationAimEvent.id]?.first()
                ?: fail("Error event not found")
            assert(resInvAim is RequestFailedEvent)
            val failAimEvent = resInvAim as RequestFailedEvent
            assert(failAimEvent.requestId == invalidReservationAimEvent.id)
            assert(failAimEvent.message == RequestFailedMessages.emptyWorkoutAim)
            val resInvDate = workoutManager
                .produce(invalidReservationDateEvent)[invalidReservationDateEvent.id]?.first()
                ?: fail("Error event not found")
            assert(resInvDate is RequestFailedEvent)
            val failDateEvent = resInvDate as RequestFailedEvent
            assert(failDateEvent.requestId == invalidReservationDateEvent.id)
            assert(failDateEvent.message == RequestFailedMessages.pastDateInReservation)
        }
        "produce events for agenda, member and ledger" - {
            val resNewMemberMap = workoutManager
                .produce(createValidWorkoutReservation)
            resNewMemberMap[createValidWorkoutReservation.id]?.first() ?: fail("Success event not found")
            val agendaList = resNewMemberMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaList.first() is AgendaAddWorkoutReservationEvent)
            val ledgerList = resNewMemberMap[ledgerId] ?: fail("Ledger events not found")
            assert(ledgerList.first() is LedgerAddMemberEvent)
            val memberList = resNewMemberMap[member.id] ?: fail("Member events not found")
            assert(memberList.first() is MemberAddWorkoutReservationEvent)
            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, resNewMemberMap)

            val createSecondValidWorkoutReservation = CreateWorkoutReservationEvent(
                UUID.randomUUID(),
                validAim,
                validDate,
                member.firstName,
                member.lastName,
                member.id
            )
            val mapWithExistingMember = workoutFullManager.produce(createSecondValidWorkoutReservation)
            mapWithExistingMember[ledgerId]?.first()?: success()
        }
    }
    "An UpdateWorkoutReservationEvent occurring in WorkoutReservationManager should" - {
        "produce empty map if event is not valid" - {
            val invalidEvent = ConsultingReservationUpdateDateEvent(UUID.randomUUID(), validDate)
            val emptyMap = workoutManager.produce(invalidEvent)
            assert(emptyMap.isEmpty())
        }
        "produce an error if parameters are not valid" - {
            val updateInvalidWorkoutRequest = UpdateWorkoutReservationEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                validAim,
                updateDate
            )
            val failingRequestDueToRequestNotFound = workoutManager
                .produce(updateInvalidWorkoutRequest)[updateInvalidWorkoutRequest.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailedEvent
            assert(failEvent.requestId == updateInvalidWorkoutRequest.id)
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

            val updateInvalidWorkoutDueToEmptyAimRequest = UpdateWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                invalidAim,
                updateDate
            )
            val requestFailAimWorkoutMap = manager.produce(updateInvalidWorkoutDueToEmptyAimRequest)

            val failUpdate =
                requestFailAimWorkoutMap[updateInvalidWorkoutDueToEmptyAimRequest.id]
                ?.first() ?: fail("Success event not found")
            assert(failUpdate is RequestFailedEvent)
            val failEvent = failUpdate as RequestFailedEvent
            assert(failEvent.requestId == updateInvalidWorkoutDueToEmptyAimRequest.id)
            assert(failEvent.message == RequestFailedMessages.emptyWorkoutAim)

            val updateInvalidWorkoutDueToPastDateRequest = UpdateWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                validAim,
                invalidDate
            )
            val requestFailDateWorkoutMap = manager.produce(updateInvalidWorkoutDueToPastDateRequest)

            val failUpdatePastDate =
                requestFailDateWorkoutMap[updateInvalidWorkoutDueToPastDateRequest.id]
                    ?.first() ?: fail("Success event not found")
            assert(failUpdatePastDate is RequestFailedEvent)
            val failEventPastDate = failUpdatePastDate as RequestFailedEvent
            assert(failEventPastDate.requestId == updateInvalidWorkoutDueToPastDateRequest.id)
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

            val closeWorkout = CloseWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                createValidWorkoutReservation.memberId
            )
            val requestWorkoutMap = manager.produce(closeWorkout)

            val managerFailUpdate = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)

            val updateWorkoutDate = UpdateWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                updateAim,
                updateDate
            )
            val resUpdateFailMap = managerFailUpdate.produce(updateWorkoutDate)
            val failUpdate =
                resUpdateFailMap[updateWorkoutDate.id]?.first() ?: fail("Success event not found")
            assert(failUpdate is RequestFailedEvent)
            val failMessage = failUpdate as RequestFailedEvent
            assert(failMessage.message == RequestFailedMessages.noUpdateToCloseReservation)
        }
        "produce event for agenda, member" - {
            val workoutMap = workoutManager.produce(createValidWorkoutReservation)
            val createRes = workoutMap[createValidWorkoutReservation.id]?.first() ?: fail("Success event not found")
            assert(createRes is RequestSucceededEvent)

            val agendaProjection = AgendaProjection(agendaId)
            val ag = workoutMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(ag.retrieveWorkoutReservation().first().aim == validAim)
            val resId = ag.retrieveWorkoutReservation().first().id
            val manager = WorkoutReservationManager(agendaId, ledgerId, workoutMap)

            val updateWorkoutDate = UpdateWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                updateAim,
                updateDate
            )
            val requestWorkoutMap = manager.produce(updateWorkoutDate)
            val updateEventMap = requestWorkoutMap[updateWorkoutDate.id]?.first() ?: fail("Success event not found")
            assert(updateEventMap is RequestSucceededEvent)

            val reservationEventMap = requestWorkoutMap[resId] ?: fail("Reservation events not found")
            assert(reservationEventMap.any { event -> event is WorkoutReservationUpdateAimEvent })
            assert(reservationEventMap.any { event -> event is WorkoutReservationUpdateDateEvent })
            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)
            val resOldMemberMap = workoutFullManager.produce(updateWorkoutDate)
            val failUpdate =
                resOldMemberMap[updateWorkoutDate.id]?.first() ?: fail("Success event not found")
            assert(failUpdate is RequestFailedEvent)
        }
    }
    "When a DeleteWorkoutReservationEvent occurs in the WorkoutReservationManager should" - {
        "produce an error if parameters are not valid" - {
            val deleteInvalidWorkoutRequest = DeleteWorkoutReservationEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                member.id
            )
            val failingRequestDueToRequestNotFound = workoutManager
                .produce(deleteInvalidWorkoutRequest)[deleteInvalidWorkoutRequest.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailedEvent
            assert(failEvent.requestId == deleteInvalidWorkoutRequest.id)
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

            val deleteInvalidMemberWorkoutRequest = DeleteWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val managerWithReservation = WorkoutReservationManager(agendaId, ledgerId, workoutManagerMap)
            val failingRequestDueToRequestNotFound = managerWithReservation
                .produce(deleteInvalidMemberWorkoutRequest)[deleteInvalidMemberWorkoutRequest.id]?.first()
                ?: fail("Error in request")
            val failEvent = failingRequestDueToRequestNotFound as RequestFailedEvent
            assert(failEvent.requestId == deleteInvalidMemberWorkoutRequest.id)
            assert(failEvent.message == RequestFailedMessages.memberNotFound)

            val createValidWorkoutReservationMember1 = CreateWorkoutReservationEvent(
                UUID.randomUUID(),
                validAim,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val mapWithAnotherReservations = managerWithReservation.produce(createValidWorkoutReservationMember1)
            val mapWith2Reservation = mapWithAnotherReservations.entries.fold(workoutManagerMap){
                map,entries ->
                    val list = map.getOrDefault(entries.key, listOf()) + entries.value
                    map + (entries.key to list)
            }
            val managerWith2Reservations = WorkoutReservationManager(agendaId, ledgerId, mapWith2Reservation)

            val deleteWrongMemberWorkoutRequest = DeleteWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                member1.id
            )
            val failingRequestDueToWrongMember = managerWith2Reservations
                .produce(deleteWrongMemberWorkoutRequest)[deleteWrongMemberWorkoutRequest.id]?.first()
                ?: fail("Error in request")
            val failEventWrongMember = failingRequestDueToWrongMember as RequestFailedEvent
            assert(failEventWrongMember.requestId == deleteWrongMemberWorkoutRequest.id)
            assert(failEventWrongMember.message == RequestFailedMessages.wrongMember)
        }
        "produce event for agenda and member" - {
            val createMap = workoutManager.produce(createValidWorkoutReservation)
            createMap[createValidWorkoutReservation.id]?.first() ?: fail("Success event not found")
            val manager = WorkoutReservationManager(agendaId, ledgerId, createMap)

            val agendaProjection = AgendaProjection(agendaId)
            val agenda = createMap[agendaId]
                ?.fold(agendaProjection.init) { ag, ev -> agendaProjection.update(ag, ev) }
                ?: fail("Reservation not found into the agenda")
            assert(agenda.retrieveWorkoutReservation().first().aim == validAim)
            val resId = agenda.retrieveWorkoutReservation().first().id

            val deleteWorkout = DeleteWorkoutReservationEvent(
                UUID.randomUUID(),
                resId,
                createValidWorkoutReservation.memberId
            )

            val requestWorkoutMap = manager.produce(deleteWorkout)
            requestWorkoutMap[deleteWorkout.id]?.first() ?: fail("Success event not found")
            val agendaList = requestWorkoutMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaList.first() is AgendaDeleteWorkoutReservationEvent)
            val memberList = requestWorkoutMap[member.id] ?: fail("Member events not found")
            assert(memberList.first() is MemberDeleteWorkoutReservationEvent)
            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, requestWorkoutMap)
            val resOldMemberMap = workoutFullManager.produce(deleteWorkout)
            resOldMemberMap[deleteWorkout.id]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
        }
    }
})
