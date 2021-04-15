/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.LedgerAddMember
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberAddWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.member.MemberDeleteWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDate
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateFreelancer
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateAim
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateDate
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.usecases.projections.AgendaProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberLedgerProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.MemberProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.OpenConsultingReservationProjection
import it.unibo.lss.fcla.reservation.domain.usecases.projections.OpenWorkoutReservationProjection
import java.util.Calendar
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

class ProjectionTest : FreeSpec({
    val agendaId = UUID.randomUUID()
    val ledgerId = UUID.randomUUID()
    val freelancerName = UUID.randomUUID()
    val freelancerName2 = UUID.randomUUID()
    val aim = "aim"
    val aim2 = "blind"
    val member = Member("first", "last", UUID.randomUUID())
    val agendaProjection = AgendaProjection(agendaId)
    val ledgerProjection = MemberLedgerProjection(ledgerId)
    val memberProjection = MemberProjection(member)
    val calendar = Calendar.getInstance()
    val validYear = 2023
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
            val addEvent = AgendaAddConsultingReservation(UUID.randomUUID(), reservationOC)
            val fullAgenda = agendaProjection.update(agendaProjection.init, addEvent)
            fullAgenda.retrieveConsultingReservation().shouldContain(reservationOC)
            val delEvent = AgendaDeleteConsultingReservation(UUID.randomUUID(), reservationOC)
            val emptyAgenda = agendaProjection.update(fullAgenda, delEvent)
            emptyAgenda.retrieveConsultingReservation().shouldBeEmpty()
        }
        "add and delete a WorkoutReservation" - {
            val addEvent = AgendaAddWorkoutReservation(UUID.randomUUID(), reservationOW)
            val fullAgenda = agendaProjection.update(agendaProjection.init, addEvent)
            fullAgenda.retrieveWorkoutReservation().shouldContain(reservationOW)
            val delEvent = AgendaDeleteWorkoutReservation(UUID.randomUUID(), reservationOW)
            val emptyAgenda = agendaProjection.update(fullAgenda, delEvent)
            emptyAgenda.retrieveWorkoutReservation().shouldBeEmpty()
        }
    }
    "MemberLedger projection should" - {
        "add a member" - {
            val event = LedgerAddMember(UUID.randomUUID(), member)
            val ledger = ledgerProjection.update(ledgerProjection.init, event)
            ledger.retrieveAllMembers().shouldContain(member)
        }
    }
    "Member projection should" - {
        "add and delete a ConsultingReservation" - {
            val eventAdd = MemberAddConsultingReservation(UUID.randomUUID(), reservationOC)
            val memberFull = memberProjection.update(memberProjection.init, eventAdd)
            memberFull.retrieveConsultingReservation().shouldContain(reservationOC)
            val eventDelete = MemberDeleteConsultingReservation(UUID.randomUUID(), reservationOC)
            val memberEmpty = memberProjection.update(memberFull, eventDelete)
            memberEmpty.retrieveConsultingReservation().shouldBeEmpty()
        }
        "add and delete a WorkoutReservation" - {
            val eventAdd = MemberAddWorkoutReservation(UUID.randomUUID(), reservationOW)
            val memberFull = memberProjection.update(memberProjection.init, eventAdd)
            memberFull.retrieveWorkoutReservation().shouldContain(reservationOW)
            val eventDelete = MemberDeleteWorkoutReservation(UUID.randomUUID(), reservationOW)
            val memberEmpty = memberProjection.update(memberFull, eventDelete)
            memberEmpty.retrieveWorkoutReservation().shouldBeEmpty()
        }
    }
    "OpenConsultingReservation projection should" - {
        "update freelancer and date" - {
            val updateDate = ConsultingReservationUpdateDate(UUID.randomUUID(), validDateLate)
            val resDate = reservationConsProjection.update(reservationConsProjection.init, updateDate)
            val updateFreelancer = ConsultingReservationUpdateFreelancer(UUID.randomUUID(), freelancerName2)
            val resFreelancer = reservationConsProjection.update(reservationConsProjection.init, updateFreelancer)
            resDate.date.shouldBe(validDateLate)
            resDate.freelancerId.value.shouldBe(freelancerName)
            resFreelancer.date.shouldBe(validDate)
            resFreelancer.freelancerId.value.shouldBe(freelancerName2)
        }
    }
    "OpenWorkoutReservation projection should" - {
        "update aim and date" - {
            val updateDate = WorkoutReservationUpdateDate(UUID.randomUUID(), validDateLate)
            val resDate = reservationWorkProjection.update(reservationWorkProjection.init, updateDate)
            val updateAim = WorkoutReservationUpdateAim(UUID.randomUUID(), aim2)
            val resAim = reservationWorkProjection.update(reservationWorkProjection.init, updateAim)
            resDate.date.shouldBe(validDateLate)
            resDate.aim.value.shouldBe(aim)
            resAim.date.shouldBe(validDate)
            resAim.aim.value.shouldBe(aim2)
        }
    }
})
