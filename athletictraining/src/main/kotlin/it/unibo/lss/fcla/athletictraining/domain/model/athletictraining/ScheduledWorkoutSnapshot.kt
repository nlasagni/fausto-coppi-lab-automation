package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import java.time.LocalDate
import java.time.LocalTime

/**
 * A snapshot class used to expose all the information about a [ScheduledWorkout].
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
data class ScheduledWorkoutSnapshot(
    private val workoutId: WorkoutId,
    private val day: LocalDate,
    private val time: LocalTime
)
