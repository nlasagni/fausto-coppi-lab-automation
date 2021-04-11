package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.service.exception.WorkoutDurationMustBeGreaterThanZero

/**
 * Concrete implementation of an [WorkoutTotalDurationCalculator] service.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class WorkoutTotalDurationCalculatorImpl : WorkoutTotalDurationCalculator {

    override fun computeTotalDuration(exercises: Collection<Exercise>): Duration {
        var totalSeconds = 0L
        for (exercise in exercises) {
            val snapshot = exercise.snapshot()
            totalSeconds +=
                snapshot.durationOfExecution.seconds + snapshot.durationOfRest.seconds
        }
        if (totalSeconds == 0L) {
            throw WorkoutDurationMustBeGreaterThanZero()
        }
        return Duration(totalSeconds)
    }
}
