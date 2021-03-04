package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import java.util.Date
import java.util.UUID

/**
 * It is referred to a reservation which can still be updated expressing [date], [freelancerId] and [id]
 *
 * Throws [ConsultingReservationFreelancerCannotBeEmpty] if an OpenConsultingReservation
 * is created without freelancer
 *
 * Throws [OpenReservationMustNotHavePastDate] if an OpenWorkoutReservation is created with a past date
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
     * Returns an [OpenConsultingReservation] updating the date of the consulting with a [newDate].
     *
     * Throws [OpenReservationMustNotHavePastDate] exception if a past date is inserted in the
     * moment of creation.
     */
    fun updateDateOfConsulting(date: Date): OpenConsultingReservation {
        if (date.before(this.date)) throw OpenReservationMustNotHavePastDate()
        return OpenConsultingReservation(date, freelancerId, id)
    }

    /**
     * Returns a new [OpenConsultingReservation] updating the [freelancerId] of a consulting
     *
     * Throws [ConsultingReservationFreelancerCannotBeEmpty] exception if the freelancer is not
     * inserted in the moment of creation.
     */
    fun updateFreelancerOfConsulting(freelancerId: String): OpenConsultingReservation {
        if (freelancerId.isEmpty()) throw ConsultingReservationFreelancerCannotBeEmpty()
        return OpenConsultingReservation(date, freelancerId, id)
    }

    override fun toString(): String = "Reservation consulting {$id} with freelancerId: $freelancerId in date $date"
}
