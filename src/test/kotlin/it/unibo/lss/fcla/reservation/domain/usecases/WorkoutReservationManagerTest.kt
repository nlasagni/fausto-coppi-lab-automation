package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.assertions.fail
import io.kotest.core.spec.style.FreeSpec
import io.kotest.fp.success
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMemberEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.results.RequestFailedMessages
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
    calendar.set(invalidYear, feb, day)
    val invalidDate = calendar.time
    val member1 = Member("Mario", "Rossi", UUID.randomUUID())
    val validAim = "Injury recovery"
    val invalidAim = ""
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
            val validReservationNewMemberEvent = CreateWorkoutReservationEvent(
                UUID.randomUUID(),
                validAim,
                validDate,
                member1.firstName,
                member1.lastName,
                member1.id
            )
            val resNewMemberMap = workoutManager
                .produce(validReservationNewMemberEvent)
            resNewMemberMap[validReservationNewMemberEvent.id]?.first() ?: fail("Success event not found")
            val agendaList = resNewMemberMap[agendaId] ?: fail("Agenda events not found")
            assert(agendaList.first() is AgendaAddWorkoutReservationEvent)
            val ledgerList = resNewMemberMap[ledgerId] ?: fail("Ledger events not found")
            assert(ledgerList.first() is LedgerAddMemberEvent)
            val memberList = resNewMemberMap[member1.id] ?: fail("Member events not found")
            assert(memberList.first() is MemberAddWorkoutReservationEvent)
            val workoutFullManager = WorkoutReservationManager(agendaId, ledgerId, resNewMemberMap)
            val resOldMemberMap = workoutFullManager
                .produce(validReservationNewMemberEvent)
            resOldMemberMap[validReservationNewMemberEvent.id]?.first() ?: fail("Success event not found")
            resOldMemberMap[ledgerId] ?: success()
        }
    }
})
