package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.domain.exceptions.CloseConsultingCannotBeUpdated
import it.unibo.lss.fcla.reservation.domain.exceptions.ConsultingReservationMustHaveFreelancer
import java.util.*

/**
 * It is referred to a consulting that cannot be updated anymore
 */
class CloseConsultingReservation(
    override val date: Date,
    override val freelancerId: String
) : ConsultingReservation {

    private val id: String
    init{
        if (freelancerId.isEmpty()) {
            throw ConsultingReservationMustHaveFreelancer()
        }
        id = "CloseConsultingReservation-${freelancerId}-${date.time}"
    }

    override fun updateDateOfConsulting(date: Date): CloseConsultingReservation {
        throw CloseConsultingCannotBeUpdated()
    }
    override fun updateFreelancerOfConsulting(freelancerId: String): CloseConsultingReservation {
        throw CloseConsultingCannotBeUpdated()
    }
}
