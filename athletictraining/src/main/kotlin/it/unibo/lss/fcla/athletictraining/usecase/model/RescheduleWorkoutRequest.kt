package it.unibo.lss.fcla.athletictraining.usecase.model

import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule

/**
 * Class that represents the request coming from outer layer of
 * rescheduling a previously scheduled workout.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class RescheduleWorkoutRequest(
    val activeAthleticTrainingId: String,
    val workoutId: String,
    val oldSchedule: Schedule,
    val newSchedule: Schedule
)
