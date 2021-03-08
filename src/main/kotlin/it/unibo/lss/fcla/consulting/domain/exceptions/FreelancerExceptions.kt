package it.unibo.lss.fcla.consulting.domain.exceptions

import it.unibo.lss.fcla.consulting.domain.consulting.Consulting

/**
 *
 */
class FreelancerFirstNameCannotBeNull : ConsultingException("Freelancer fist name cannot be null or empty")

/**
 *
 */
class FreelancerLastNameCannotBeNull : ConsultingException("Freelancer last name cannot be null or empty")

/**
 *
 */
class FreelancerMustHaveAValidId : ConsultingException("Freelancer must have a valid Id")

/**
 *
 */
class FreelancerAvailabilityNotValidTime :
    ConsultingException("The fromTime of an availability must be smaller than toTime")

/**
 *
 */
class FreelancerAvailabilityAlreadyExist : ConsultingException("An availability already exists for the given date")

/**
 *
 */
class FreelancerAvailabilityDoesNotExist : ConsultingException("An availability does not exist for the given date")