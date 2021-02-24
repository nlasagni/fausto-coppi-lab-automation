package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.domain.exceptions.ChooseAnotherDateForTheConsulting
import it.unibo.lss.fcla.reservation.domain.exceptions.ConsultingReservationMustHaveFreelancer
import java.util.Date

/**
 * It is referred to a reservation which can still be updated expressing [date] and [freelancerId]
 */
class OpenConsultingReservation(
    override val date: Date,
    override val freelancerId: String
    ): ConsultingReservation {
        private val id: String
        init {
            if (freelancerId.isEmpty()) throw ConsultingReservationMustHaveFreelancer()
            id = "$freelancerId-${date.time}"
        }

    /**
     * Method used to update the date of the consulting with a [newDate]
     *
     * @return A new OpenConsultingReservation with the new date
     */
    fun updateDateOfConsulting(newDate: Date): OpenConsultingReservation {
        if (newDate.before(date)) throw ChooseAnotherDateForTheConsulting()
        else return OpenConsultingReservation(newDate, freelancerId)
    }

    /**
     * Method used to update the freelancer of a consulting with the [newFreelancerId]
     *
     * @return A new OpenConsultingReservation with a new freelancer
     */
    fun updateFreelancerOfConsulting(newFreelancerId: String): OpenConsultingReservation {
        if (newFreelancerId.isEmpty()) throw ConsultingReservationMustHaveFreelancer()
        return OpenConsultingReservation(date, newFreelancerId)
    }

    override fun toString(): String = "Reservation consulting {$id} with freelancerId: $freelancerId"
}
