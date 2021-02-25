package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.CloseReservationCannotBeUpdated
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import java.util.Date

/**
 * It is referred to a consulting that cannot be updated anymore
 */
class CloseConsultingReservation(
    override val date: Date,
    override val freelancerId: String
) : ConsultingReservation {

    private val id: String
    init {
        if (freelancerId.isEmpty()) throw ConsultingReservationFreelancerCannotBeEmpty()
        id = "CloseConsultingReservation-$freelancerId-${date.time}"
    }

    override fun updateDateOfConsulting(date: Date): CloseConsultingReservation {
        throw CloseReservationCannotBeUpdated()
    }

    override fun updateFreelancerOfConsulting(freelancerId: String): CloseConsultingReservation {
        throw CloseReservationCannotBeUpdated()
    }

    fun getID(): String {
        return this.id
    }
}
