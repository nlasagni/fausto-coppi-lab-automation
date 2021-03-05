package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateAimEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.WorkoutReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenWorkoutReservation

/**
 * Projection used to update the [OpenWorkoutReservation] given its events
 */
class OpenWorkoutReservationProjection(override val init: OpenWorkoutReservation) : Projection<OpenWorkoutReservation> {

    /**
     * Return an updated [OpenWorkoutReservation] based on the given event.
     */
    override fun update(state: OpenWorkoutReservation, event: Event) = when (event) {
        is WorkoutReservationUpdateDateEvent -> state.updateWorkoutReservationDate(event.date)
        is WorkoutReservationUpdateAimEvent -> state.updateWorkoutReservationAim(event.aim)
        else -> state
    }
}
