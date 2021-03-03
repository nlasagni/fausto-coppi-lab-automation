package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateDateEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.reservation.ConsultingReservationUpdateFreelancerEvent
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation

class OpenConsultingReservationProjection(val init: OpenConsultingReservation) {

    fun update(openConsultingReservation: OpenConsultingReservation, event: Event) = when (event) {
        is ConsultingReservationUpdateDateEvent -> openConsultingReservation.updateDateOfConsulting(event.date)
        is ConsultingReservationUpdateFreelancerEvent ->
            openConsultingReservation.updateFreelancerOfConsulting(event.freelancer)
        else -> openConsultingReservation
    }
}
