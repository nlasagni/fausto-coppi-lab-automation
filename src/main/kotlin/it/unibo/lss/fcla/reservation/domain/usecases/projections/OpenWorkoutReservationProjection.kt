package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateAimEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation

class OpenWorkoutReservationProjection(val init: OpenWorkoutReservation) {

    fun update(openWorkoutReservation: OpenWorkoutReservation, event: Event) = when (event) {
        is WorkoutReservationUpdateDateEvent -> openWorkoutReservation.updateWorkoutReservationDate(event.date)
        is WorkoutReservationUpdateAimEvent -> openWorkoutReservation.updateWorkoutReservationAim(event.aim)
        else -> openWorkoutReservation
    }
}
