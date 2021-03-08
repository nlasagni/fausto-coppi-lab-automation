package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException

/**
 *
 */
class ConsultingShouldHaveAUniqueId : ConsultingException("A consulting with the same Id already exist")

/**
 *
 */
class ConsultingWithGivenIdDoesNotExist : ConsultingException("A consulting with the given Id does not exist")