package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import java.util.Date
import java.util.UUID

/**
 * It is referred to a consulting reservation that cannot be updated anymore
 */
class CloseConsultingReservation(
    override val date: Date,
    private val myFreelancerId: UUID,
    override val id: UUID
) : ConsultingReservation {

    override val freelancerId = FreelancerId(myFreelancerId)

    override fun toString(): String = "Reservation consulting {$id} with freelancerId: $myFreelancerId in date $date"

    override fun equals(other: Any?): Boolean { return (other is CloseConsultingReservation) && other.id == this.id }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + freelancerId.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
