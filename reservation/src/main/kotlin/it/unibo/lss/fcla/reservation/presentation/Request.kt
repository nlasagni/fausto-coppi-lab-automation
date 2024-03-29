/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.presentation

import java.util.Date
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Interface used to group all request type
 */
interface Request

/**
 * Class that implements [Request] interface.
 *
 * This class contains information to create a consulting reservation.
 */
class RequestCreateConsultingReservation(
    val freelancer: UUID,
    val date: Date,
    val firstName: String,
    val lastName: String,
    val memberId: UUID
) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class contains information to create a workout reservation.
 */
class RequestCreateWorkoutReservation(
    val aim: String,
    val date: Date,
    val firstName: String,
    val lastName: String,
    val memberId: UUID
) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class contains information to update a consulting reservation.
 */
class RequestUpdateConsultingReservation(
    val reservationId: UUID,
    val freelancer: UUID,
    val date: Date
) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class contains information to update a workout reservation.
 */
class RequestUpdateWorkoutReservation(
    val reservationId: UUID,
    val aim: String,
    val date: Date
) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class contains information to delete a consulting reservation.
 */
class RequestDeleteConsultingReservation(
    val reservationId: UUID,
    val memberId: UUID
) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class contains information to delete a workout reservation.
 */
class RequestDeleteWorkoutReservation(
    val reservationId: UUID,
    val memberId: UUID
) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class represents the request to retrieve agenda consulting reservations.
 */
class RequestRetrieveAgendaConsultingReservation : Request

/**
 * Class that implements [Request] interface.
 *
 * This class represents the request to retrieve agenda workout reservations.
 */
class RequestRetrieveAgendaWorkoutReservation : Request

/**
 * Class that implements [Request] interface.
 *
 * This class represents the request to retrieve consulting reservation given its [reservationId]
 */
class RequestRetrieveConsultingReservation(val reservationId: UUID) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class represents the request to retrieve workout reservation given its [reservationId].
 */
class RequestRetrieveWorkoutReservation(val reservationId: UUID) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class represents the request to retrieve member consulting reservation given the [memberId].
 */
class RequestRetrieveMemberConsultingReservations(val memberId: UUID) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class represents the request to retrieve workout reservation given the [memberId].
 */
class RequestRetrieveMemberWorkoutReservations(val memberId: UUID) : Request
