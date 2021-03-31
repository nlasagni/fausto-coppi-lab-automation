package it.unibo.lss.fcla.athletictraining.domain.model

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout

/**
 * A snapshot class used to expose all the information about a [TrainingPlan].
 *
 * @author Nicola Lasagni on 03/03/2021.
 */
data class TrainingPlanSnapshot(
    val name: String,
    val purpose: Purpose,
    val periodOfTraining: PeriodOfTraining,
    val workouts: List<Workout>
)
