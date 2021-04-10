package it.unibo.lss.fcla.athletictraining.usecase.exception

/**
 * Thrown to indicate that has not been possible to remove the desired athletic training.
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
class ActiveAthleticTrainingNotRemoved :
    UseCaseException("An error occurred while removing the desired athletic training.")
