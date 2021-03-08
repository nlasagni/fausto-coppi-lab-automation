package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.domain.exceptions.ConsultingException

/**
 *
 */
class ConsultingShouldHaveAUniqueId : ConsultingException("A consulting with the same is already exist")