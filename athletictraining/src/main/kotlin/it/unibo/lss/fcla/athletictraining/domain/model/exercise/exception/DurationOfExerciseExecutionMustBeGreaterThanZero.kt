package it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception

/**
 * Thrown to indicate that the Duration of execution is lower than or equal to zero.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
class DurationOfExerciseExecutionMustBeGreaterThanZero :
    Exception("The duration of execution for an exercise must be greater than zero.")
