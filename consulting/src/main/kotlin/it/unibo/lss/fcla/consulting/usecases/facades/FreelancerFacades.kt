package it.unibo.lss.fcla.consulting.usecases.facades

import it.unibo.lss.fcla.consulting.domain.freelancer.AvailabilityHours
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Stefano Braggion
 */

/**
 *
 */
class FreelancerFacade internal constructor(
    val freelancerId: FreelancerId,
    val firstName: String,
    val lastName: String,
    val role: FreelancerRole
) {

    companion object {

        /**
         * Factory
         */
        fun create(freelancer: Freelancer) : FreelancerFacade {
            val freelancerFacade = FreelancerFacade(
                freelancerId = freelancer.freelancerId,
                firstName = freelancer.getFirstName(),
                lastName = freelancer.getLastName(),
                role = freelancer.getRole()
            );

            return freelancerFacade;
        }
    }
}

/**
 *
 */
class FreelancerAvailabilityFacade internal constructor(
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) {

    companion object {

        /**
         * Factory
         */
        fun create(date: LocalDate, hours: AvailabilityHours) : FreelancerAvailabilityFacade {
            val availabilityFacade = FreelancerAvailabilityFacade(
                availabilityDate = date,
                fromTime = hours.fromTime,
                toTime = hours.toTime
            )

            return availabilityFacade
        }
    }
}