package it.unibo.lss.fcla.athleticpreparation.domain.exception

/**
 * @author Nicola Lasagni on 03/03/2021.
 */
class AthleticPreparationAlreadyCompleted :
    Exception("This athletic preparation has already been completed, the operation cannot be performed.")
