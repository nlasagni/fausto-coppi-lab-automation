package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateFreelancerEvent
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation

class OpenConsultingReservationProjection(override val init: OpenConsultingReservation) :
        Projection<OpenConsultingReservation> {

    override fun update(state: OpenConsultingReservation, event: Event) = when (event) {
        is ConsultingReservationUpdateDateEvent -> state.updateDateOfConsulting(event.date)
        is ConsultingReservationUpdateFreelancerEvent ->
            state.updateFreelancerOfConsulting(event.freelancer)
        else -> state
    }
}
