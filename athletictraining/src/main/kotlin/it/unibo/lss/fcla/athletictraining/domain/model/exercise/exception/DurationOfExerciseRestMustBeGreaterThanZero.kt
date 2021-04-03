package it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception

/**
 * @author Nicola Lasagni on 02/04/2021.
 */
class DurationOfExerciseRestMustBeGreaterThanZero :
    Exception("The duration of rest for an exercise must be greater than zero.")
