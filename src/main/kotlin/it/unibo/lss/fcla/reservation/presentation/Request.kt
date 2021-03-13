package it.unibo.lss.fcla.reservation.presentation

import java.util.Date
import java.util.UUID

interface Request

sealed class RequestCreateConsultingReservation (
    val freelancer: UUID,
    val date: Date,
    val firstName: String,
    val lastName: String,
    val memberId: UUID
) : Request

sealed class RequestCreateWorkoutReservation (
    val aim: String,
    val date: Date,
    val firstName: String,
    val lastName: String,
    val memberId: UUID
) : Request

sealed class RequestUpdateConsultingReservation(
    val reservationId: UUID,
    val freelancer: UUID,
    val date: Date
) : Request

sealed class RequestUpdateWorkoutReservation (
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
sealed class RequestDeleteConsultingReservation (
        val reservationId: UUID,
        val memberId: UUID
): Request

/**
 * Sealed class that implements [Request] interface.
 *
 * This class performs instruction for a requestDeleteWorkoutReservation
 * with no logic in it.
 */
sealed class RequestDeleteWorkoutReservation(
        val reservationId: UUID,
        val memberId: UUID
): Request

sealed class RequestRetrieveAgendaConsultingReservation : Request

sealed class RequestRetrieveAgendaWorkoutReservation : Request

sealed class RequestRetrieveConsultingReservation(reservationId: UUID) : Request

sealed class RequestRetrieveWorkoutReservation(reservationId: UUID) : Request

sealed class RequestRetrieveMemberConsultingReservations(memberId: UUID) : Request

sealed class RequestRetrieveMemberWorkoutReservations(memberId: UUID) : Request
