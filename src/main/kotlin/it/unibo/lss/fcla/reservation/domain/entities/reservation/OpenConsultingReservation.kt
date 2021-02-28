package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import java.util.Date
import java.util.UUID

/**
 * It is referred to a reservation which can still be updated expressing [date] and [freelancerId]
 */
class OpenConsultingReservation(
    override val date: Date,
    override val freelancerId: String,
    override val id: UUID
) : ConsultingReservation {

    init {
        if (freelancerId.isEmpty()) throw ConsultingReservationFreelancerCannotBeEmpty()
        if (date.before(Date())) throw OpenReservationMustNotHavePastDate()
    }

    /**
     * Returns an [OpenConsultingReservation] updating the date of the consulting with a [newDate]
     */
    fun updateDateOfConsulting(date: Date): OpenConsultingReservation {
        if (date.before(this.date)) throw OpenReservationMustNotHavePastDate()
        return OpenConsultingReservation(date, freelancerId, id)
    }

    /**
     * Method used to update the freelancer of a consulting with the [newFreelancerId]
     *
     * @return A new OpenConsultingReservation with a new freelancer
     */
    fun updateFreelancerOfConsulting(freelancerId: String): OpenConsultingReservation {
        if (freelancerId.isEmpty()) throw ConsultingReservationFreelancerCannotBeEmpty()
        return OpenConsultingReservation(date, freelancerId, id)
    }

    fun value(): UUID {
        return id
    }

    override fun toString(): String = "Reservation consulting {$id} with freelancerId: $freelancerId in date $date"
}
