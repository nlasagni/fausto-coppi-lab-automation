package it.unibo.lss.fcla.consulting.domain.exceptions

/**
 * Thrown when a freelancer is created without a First Name
 */
class FreelancerFirstNameCannotBeNull : ConsultingException("Freelancer fist name cannot be null or empty")

/**
 * Thrown when a freelancer is created without a Last Name
 */
class FreelancerLastNameCannotBeNull : ConsultingException("Freelancer last name cannot be null or empty")

/**
 * Thrown when a freelancer is created without a valid Freelancer Id
 */
class FreelancerMustHaveAValidId : ConsultingException("Freelancer must have a valid Id")

/**
 * Thrown when a freelancer availability is created with a starting time smaller than end time
 */
class FreelancerAvailabilityNotValidTime :
    ConsultingException("The fromTime of an availability must be smaller than toTime")

/**
 * Thrown when a freelancer availability is created for a specific date, but another one already exist in that date
 */
class FreelancerAvailabilityAlreadyExist : ConsultingException("An availability already exists for the given date")

/**
 * Thrown when a freelancer availability is updated, but not availability exist for the given date
 */
class FreelancerAvailabilityDoesNotExist : ConsultingException("An availability does not exist for the given date")
