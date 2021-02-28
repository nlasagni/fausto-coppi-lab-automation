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
    override val freelancerId: String,
    override val id: UUID
) : ConsultingReservation {

    init {
        if (freelancerId.isEmpty()) throw ConsultingReservationFreelancerCannotBeEmpty()
    }

    /**
     * This methods return the [UUID] of the consulting reservation
     */
    fun value(): UUID {
        return id
    }

    override fun toString(): String = "Reservation consulting {$id} with freelancerId: $freelancerId in date $date"
}
