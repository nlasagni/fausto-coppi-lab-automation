package it.unibo.lss.fcla.reservation.presentation

import java.util.Date
import java.util.UUID

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
 * This class represents the request to retrieve consulting reservation given its id.
 */
class RequestRetrieveConsultingReservation(reservationId: UUID) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class represents the request to retrieve workout reservation given its id.
 */
class RequestRetrieveWorkoutReservation(reservationId: UUID) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class represents the request to retrieve member consulting reservation given the id of the member.
 */
class RequestRetrieveMemberConsultingReservations(memberId: UUID) : Request

/**
 * Class that implements [Request] interface.
 *
 * This class represents the request to retrieve workout reservation given the id of the member.
 */
class RequestRetrieveMemberWorkoutReservations(memberId: UUID) : Request
