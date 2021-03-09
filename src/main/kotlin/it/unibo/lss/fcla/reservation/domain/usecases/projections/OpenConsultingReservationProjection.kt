package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDate
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateFreelancer
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation

/**
 * Projection used to update the [OpenConsultingReservation] given its events
 */
class OpenConsultingReservationProjection(override val init: OpenConsultingReservation) :
    Projection<OpenConsultingReservation> {

    /**
     * Return an updated [OpenConsultingReservation] based on the given event.
     */
    override fun update(state: OpenConsultingReservation, event: Event) = when (event) {
        is ConsultingReservationUpdateDate -> state.updateDateOfConsulting(event.date)
        is ConsultingReservationUpdateFreelancer ->
            state.updateFreelancerOfConsulting(event.freelancer)
        else -> state
    }
}
