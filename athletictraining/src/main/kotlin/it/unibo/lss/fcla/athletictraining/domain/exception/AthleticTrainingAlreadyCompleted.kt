package it.unibo.lss.fcla.athletictraining.domain.exception

/**
 * @author Nicola Lasagni on 03/03/2021.
 */
class AthleticTrainingAlreadyCompleted :
    Exception("This athletic training has already been completed, the operation cannot be performed.")
