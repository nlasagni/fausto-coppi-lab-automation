package it.unibo.lss.fcla.athletictraining.usecase.exception

/**
 * Thrown to indicate that the provided athletic training identifier
 * is not valid.
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
class ActiveAthleticTrainingNotFound :
    UseCaseException("Athletic training with provided identifier not found.")
