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
) : BaseFacade {

    companion object {

        /**
         * Factory
         */
        fun create(freelancer: Freelancer): FreelancerFacade {

            return FreelancerFacade(
                freelancerId = freelancer.freelancerId,
                firstName = freelancer.getPersonalData().firstName,
                lastName = freelancer.getPersonalData().lastName,
                role = freelancer.getPersonalData().role
            )
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
) : BaseFacade {

    companion object {

        /**
         * Factory
         */
        fun create(date: LocalDate, hours: AvailabilityHours): FreelancerAvailabilityFacade {

            return FreelancerAvailabilityFacade(
                availabilityDate = date,
                fromTime = hours.fromTime,
                toTime = hours.toTime
            )
        }
    }
}

/**
 *
 */
class FreelancerErrorFacade internal constructor(
    val message: String
) : BaseFacade {

    companion object {

        /**
         * Factory
         */
        fun create(error: String): FreelancerErrorFacade {
            return FreelancerErrorFacade(
                message = error
            )
        }
    }
}
