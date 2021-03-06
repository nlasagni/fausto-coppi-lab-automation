package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.member.*
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateFreelancerEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateAimEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.projections.*
import java.util.Calendar
import java.util.UUID

class ProjectionTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    val ledgerId = UUID.randomUUID()
    val freelancerName = "freelancer"
    val freelancerName2 = "Mandrake"
    val aim = "aim"
    val aim2 = "blind"
    val member = Member("first", "last", UUID.randomUUID())
    val agendaProjection = AgendaProjection(agendaId)
    val ledgerProjection = MemberLedgerProjection(ledgerId)
    val memberProjection = MemberProjection(member)
    val calendar = Calendar.getInstance()
    val validYear = 2022
    val validYearLate = 2028
    val feb = Calendar.FEBRUARY
    val day = 25
    calendar.set(validYear, feb, day)
    val validDate = calendar.time
    calendar.set(validYearLate, feb, day)
    val validDateLate = calendar.time
    val reservationOC = OpenConsultingReservation(validDate, freelancerName, UUID.randomUUID())
    val reservationOW = OpenWorkoutReservation(aim, validDate, UUID.randomUUID())
    val reservationConsProjection = OpenConsultingReservationProjection(reservationOC)
    val reservationWorkProjection = OpenWorkoutReservationProjection(reservationOW)
    "Agenda projection should" - {
        "add and delete a ConsultingReservation" - {
            val addEvent = AgendaAddConsultingReservationEvent(UUID.randomUUID(), reservationOC)
            val fullAgenda = agendaProjection.update(agendaProjection.init, addEvent)
            assert(fullAgenda.retrieveConsultingReservation().contains(reservationOC))
            val delEvent = AgendaDeleteConsultingReservationEvent(UUID.randomUUID(), reservationOC)
            val emptyAgenda = agendaProjection.update(fullAgenda, delEvent)
            assert(emptyAgenda.retrieveConsultingReservation().isEmpty())
        }
        "add and delete a WorkoutReservation" - {
            val addEvent = AgendaAddWorkoutReservationEvent(UUID.randomUUID(), reservationOW)
            val fullAgenda = agendaProjection.update(agendaProjection.init, addEvent)
            assert(fullAgenda.retrieveWorkoutReservation().contains(reservationOW))
            val delEvent = AgendaDeleteWorkoutReservationEvent(UUID.randomUUID(), reservationOW)
            val emptyAgenda = agendaProjection.update(fullAgenda, delEvent)
            assert(emptyAgenda.retrieveWorkoutReservation().isEmpty())
        }
    }
    "MemberLedger projection should" - {
        "add a member" - {
            val event = LedgerAddMemberEvent(UUID.randomUUID(), member)
            val ledger = ledgerProjection.update(ledgerProjection.init, event)
            assert(ledger.retrieveAllMembers().contains(member))
        }
    }
    "Member projection should" - {
        "add and delete a ConsultingReservation" - {
            val eventAdd = MemberAddConsultingReservationEvent(UUID.randomUUID(),reservationOC)
            val memberFull = memberProjection.update(memberProjection.init,eventAdd)
            assert(memberFull.retrieveConsultingReservation().contains(reservationOC))
            val eventDelete = MemberDeleteConsultingReservationEvent(UUID.randomUUID(),reservationOC)
            val memberEmpty = memberProjection.update(memberFull,eventDelete)
            assert(memberEmpty.retrieveConsultingReservation().isEmpty())
        }
        "add and delete a WorkoutReservation" - {
            val eventAdd = MemberAddWorkoutReservationEvent(UUID.randomUUID(),reservationOW)
            val memberFull = memberProjection.update(memberProjection.init,eventAdd)
            assert(memberFull.retrieveWorkoutReservation().contains(reservationOW))
            val eventDelete = MemberDeleteWorkoutReservationEvent(UUID.randomUUID(),reservationOW)
            val memberEmpty = memberProjection.update(memberFull,eventDelete)
            assert(memberEmpty.retrieveWorkoutReservation().isEmpty())
        }
    }
    "OpenConsultingReservation projection should" - {
        "update freelancer and date" - {
            val updateDate = ConsultingReservationUpdateDateEvent(UUID.randomUUID(),validDateLate)
            val resDate = reservationConsProjection.update(reservationConsProjection.init,updateDate)
            val updateFreelancer = ConsultingReservationUpdateFreelancerEvent(UUID.randomUUID(),freelancerName2)
            val resFreelancer = reservationConsProjection.update(reservationConsProjection.init,updateFreelancer)
            assert(resDate.date == validDateLate)
            assert(resDate.freelancerId == freelancerName)
            assert(resFreelancer.date == validDate)
            assert(resFreelancer.freelancerId == freelancerName2)
        }
    }
    "OpenWorkoutReservation projection should" - {
        "update aim and date" - {
            val updateDate = WorkoutReservationUpdateDateEvent(UUID.randomUUID(),validDateLate)
            val resDate = reservationWorkProjection.update(reservationWorkProjection.init,updateDate)
            val updateAim = WorkoutReservationUpdateAimEvent(UUID.randomUUID(),aim2)
            val resAim = reservationWorkProjection.update(reservationWorkProjection.init,updateAim)
            assert(resDate.date == validDateLate)
            assert(resDate.aim == aim)
            assert(resAim.date == validDate)
            assert(resAim.aim == aim2)
        }
    }
})
