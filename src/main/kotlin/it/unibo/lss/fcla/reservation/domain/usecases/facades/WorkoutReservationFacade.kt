package it.unibo.lss.fcla.reservation.domain.usecases.facades

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation
import java.util.Date
import java.util.UUID

data class WorkoutReservationFacade(
    override val date: Date,
    override val aim: String,
    override val id: UUID,
    val isOpen: Boolean
) : WorkoutReservation {

    constructor(openWorkoutReservation: OpenWorkoutReservation) : this(
        openWorkoutReservation.date,
        openWorkoutReservation.aim,
        openWorkoutReservation.id,
        isOpen = true
    )

    constructor(closeWorkoutReservation: CloseWorkoutReservation) : this(
        closeWorkoutReservation.date,
        closeWorkoutReservation.aim,
        closeWorkoutReservation.id,
        isOpen = false
    )
}
