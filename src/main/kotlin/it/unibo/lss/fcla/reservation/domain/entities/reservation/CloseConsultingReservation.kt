package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import java.util.Date
import java.util.UUID

/**
 * It is referred to a consulting reservation that cannot be updated anymore
 */
class CloseConsultingReservation(
    override val date: Date,
    override val freelancerId: UUID,
    override val id: UUID
) : ConsultingReservation {

    init {
        if (freelancerId == UUID(0, 0)) throw ConsultingReservationFreelancerCannotBeEmpty()
    }

    override fun toString(): String = "Reservation consulting {$id} with freelancerId: $freelancerId in date $date"

    override fun equals(other: Any?): Boolean { return (other is CloseConsultingReservation) && other.id == this.id }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + freelancerId.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
