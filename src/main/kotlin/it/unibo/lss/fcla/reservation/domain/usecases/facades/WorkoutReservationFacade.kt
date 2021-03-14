package it.unibo.lss.fcla.reservation.domain.usecases.facades

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import java.util.Date
import java.util.UUID

/**
 * Facade class used to have access to all workout reservation information.
 *
 * The information are: the [date] of the reservation, the [aim] of the workout reservation,
 * the [id] of the facade and [isOpen] which is used to know if a reservation is open or close.
 */
data class WorkoutReservationFacade(
    val date: Date,
    val aim: String,
    val id: UUID,
    val isOpen: Boolean
) {

    constructor(openWorkoutReservation: OpenWorkoutReservation) : this(
        openWorkoutReservation.date,
        openWorkoutReservation.aim.aim,
        openWorkoutReservation.id,
        isOpen = true
    )

    constructor(closeWorkoutReservation: CloseWorkoutReservation) : this(
        closeWorkoutReservation.date,
        closeWorkoutReservation.aim.aim,
        closeWorkoutReservation.id,
        isOpen = false
    )
}
