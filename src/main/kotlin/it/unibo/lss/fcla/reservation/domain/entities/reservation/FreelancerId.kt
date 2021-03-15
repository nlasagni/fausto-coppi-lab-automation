package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import java.util.UUID

/**
 * Freelancer value object.
 *
 * It is the unique identifier of a Freelancer identified by its [value]
 */
class FreelancerId(val value: UUID) {

    init {
        if (value == UUID(0, 0)) {
            throw ConsultingReservationFreelancerCannotBeEmpty()
        }
    }
}
