/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases.events.results

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Companion object used to express the error message that occurred during a particular workout or consulting request
 */
object RequestFailedMessages {
    const val reservationNotFound = "The requested reservation was not found."
    const val memberNotFound = "The member was not found."
    const val wrongMember = "The reservation is not for this member."
    const val alreadyCloseReservation = "The reservation is already close."
    const val emptyWorkoutAim = "Unable to create a workout reservation due to empty aim."
    const val emptyConsultingFreelancer = "Unable to create a consulting reservation due to empty freelancer."
    const val pastDateInReservation = "Unable to create a reservation due to insertion of a past date."
    const val noUpdateToCloseReservation = "Update to close reservation are not allowed."
}
