package it.unibo.lss.fcla.reservation.presentation

import java.util.UUID

/**
 * Sealed class that implements [Request] interface.
 *
 * This class performs instruction for a requestDeleteConsultingReservation
 * with no logic in it.
 */
sealed class RequestDeleteConsultingReservation(
    val reservationId: UUID,
    val memberId: UUID
) : Request
