package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import java.util.UUID

class FreelancerId(val freelancerId: UUID) {
    init {
        if (freelancerId == UUID(0, 0)) {
            throw ConsultingReservationFreelancerCannotBeEmpty()
        }
    }
}