/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import java.util.Calendar
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

class MemberLedgerTest : FreeSpec({
    val ledgerId = UUID.randomUUID()
    val memberLedger = MemberLedger(ledgerId)
    val member = Member("Mario", "Rossi", UUID.randomUUID())

    val calendar = Calendar.getInstance()
    val year = 2023
    val feb = Calendar.FEBRUARY
    val day = 25
    val freelancerId = UUID.randomUUID()
    val freelancer = UUID.randomUUID()
    val aim = "recovery"
    val aimInjury = "injury"
    calendar.set(year, feb, day)
    val consulting = OpenConsultingReservation(calendar.time, freelancerId, UUID.randomUUID())
    val consultingNotInMember = OpenConsultingReservation(calendar.time, freelancer, UUID.randomUUID())
    val workout = OpenWorkoutReservation(aim, calendar.time, UUID.randomUUID())
    val workoutNotInMember = OpenWorkoutReservation(aimInjury, calendar.time, UUID.randomUUID())

    "A member should" - {
        "be added to the ledger" - {
            val newMemberList = memberLedger.addMemberToLedger(member)
            memberLedger.id.shouldBe(newMemberList.id)
            newMemberList.retrieveAllMembers().shouldContain(member)
        }
    }
    "It should" - {
        "be possible to retrieve a member given a reservation" - {
            val memberWithConsulting = member.addConsultingReservation(consulting)
            val memberWithWorkout = memberWithConsulting.addWorkoutReservation(workout)
            val myLedgerWithConsulting = memberLedger.addMemberToLedger(memberWithConsulting)
            val myLedgerWithConsultingAndWorkout = memberLedger.addMemberToLedger(memberWithWorkout)
            myLedgerWithConsulting.retrieveMemberWithConsultingReservation(consulting)
                .shouldBe(memberWithConsulting)
            myLedgerWithConsultingAndWorkout.retrieveMemberWithWorkoutReservation(workout)
                .shouldBe(memberWithWorkout)
            shouldThrow<NoSuchElementException> {
                myLedgerWithConsulting.retrieveMemberWithConsultingReservation(consultingNotInMember)
            }
            shouldThrow<NoSuchElementException> {
                myLedgerWithConsultingAndWorkout.retrieveMemberWithWorkoutReservation(workoutNotInMember)
            }
        }
    }
})
