package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise

/**
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
        return Duration(totalSeconds)
    }
}
