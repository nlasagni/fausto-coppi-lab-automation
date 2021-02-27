package it.unibo.lss.fcla.consultingContext.domain.exceptions

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
class FreelancerAvailabilityNotValidTime :
    ConsultingException("The fromTime of an availability must be smaller than toTime")

/**
 *
 */
class FreelancerAvailabilityAlreadyExist : ConsultingException("An availability already exists for the given date")
