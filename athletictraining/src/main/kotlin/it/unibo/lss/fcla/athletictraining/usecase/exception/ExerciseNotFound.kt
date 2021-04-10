package it.unibo.lss.fcla.athletictraining.usecase.exception

/**
 * Thrown to indicate that the provided exercise identifier
 * is not valid.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class ExerciseNotFound :
    UseCaseException("Exercise with provided identifier not found.")
