package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise

/**
 * @author Nicola Lasagni on 11/04/2021.
 */
interface WorkoutTotalDurationCalculator {

    fun computeTotalDuration(exercises: Collection<Exercise>): Duration
}
