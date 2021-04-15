package it.unibo.lss.fcla.athletictraining.usecase.shared.exception

/**
 * Thrown to indicate that the provided workout identifier
 * is not valid.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class WorkoutNotFound :
    UseCaseException("Workout with provided identifier not found.")
