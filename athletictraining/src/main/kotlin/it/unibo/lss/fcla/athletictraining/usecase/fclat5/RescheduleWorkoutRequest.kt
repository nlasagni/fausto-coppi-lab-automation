package it.unibo.lss.fcla.athletictraining.usecase.fclat5

import java.time.LocalDate
import java.time.LocalTime

/**
 * Class that represents the request coming from outer layer of
 * rescheduling a previously scheduled workout.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class RescheduleWorkoutRequest(
    val activeAthleticTrainingId: String,
    val workoutId: String,
    val currentDay: LocalDate,
    val currentTime: LocalTime,
    val newDay: LocalDate,
    val newTime: LocalTime
)
