package it.unibo.lss.fcla.athletictraining.usecase.model

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule

/**
 * Class that represents the request coming from outer layer of
 * canceling a previously scheduled workout.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class CancelScheduledWorkoutRequest(
    val activeAthleticTrainingId: ActiveAthleticTrainingId,
    val workoutId: WorkoutId,
    val schedule: Schedule
)
