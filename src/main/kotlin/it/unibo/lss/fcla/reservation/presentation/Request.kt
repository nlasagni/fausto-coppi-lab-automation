package it.unibo.lss.fcla.reservation.presentation

import java.util.Date
import java.util.UUID

interface Request

class RequestCreateConsultingReservation (
    val freelancer: UUID,
    val date: Date,
    val firstName: String,
    val lastName: String,
    val memberId: UUID
) : Request

class RequestCreateWorkoutReservation (
    val aim: String,
    val date: Date,
    val firstName: String,
    val lastName: String,
    val memberId: UUID
) : Request

class RequestUpdateConsultingReservation(
    val reservationId: UUID,
    val freelancer: UUID,
    val date: Date
) : Request

class RequestUpdateWorkoutReservation (
    val reservationId: UUID,
    val aim: String,
    val date: Date
) : Request

/**
 * Sealed class that implements [Request] interface.
 *
 * This class performs instruction for a requestDeleteConsultingReservation
 * with no logic in it.
 */
class RequestDeleteConsultingReservation (
        val reservationId: UUID,
        val memberId: UUID
): Request

/**
 * Sealed class that implements [Request] interface.
 *
 * This class performs instruction for a requestDeleteWorkoutReservation
 * with no logic in it.
 */
class RequestDeleteWorkoutReservation(
        val reservationId: UUID,
        val memberId: UUID
): Request

class RequestRetrieveAgendaConsultingReservation : Request

class RequestRetrieveAgendaWorkoutReservation : Request

class RequestRetrieveConsultingReservation(reservationId: UUID) : Request

class RequestRetrieveWorkoutReservation(reservationId: UUID) : Request

class RequestRetrieveMemberConsultingReservations(memberId: UUID) : Request

class RequestRetrieveMemberWorkoutReservations(memberId: UUID) : Request
