package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException

/**
 * Thrown when a consulting is created with an already existing Id
 */
class ConsultingShouldHaveAUniqueId :
    ConsultingException("A consulting with the same Id already exist")

/**
 * Thrown when the provided consulting Id does not exist
 */
class ConsultingWithGivenIdDoesNotExist :
    ConsultingException("A consulting with the given Id does not exist")

/**
 * Thrown when a freelancer is created with an already existing Id
 */
class FreelancerShouldHaveAUniqueId :
    ConsultingException("A freelancer with the same Id already exist")

/**
 * Thrown when the provided freelancer Id does not exist
 */
class FreelancerWithGivenIdDoesNotExist :
    ConsultingException("A freelancer with the given Id does not exist")
