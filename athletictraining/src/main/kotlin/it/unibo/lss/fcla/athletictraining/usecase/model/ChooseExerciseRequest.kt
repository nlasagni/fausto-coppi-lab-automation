package it.unibo.lss.fcla.athletictraining.usecase.model

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId

/**
 * Class that represents the request coming from outer layer of
 * choosing an exercise for a workout.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class ChooseExerciseRequest(val workoutId: WorkoutId, val exerciseId: ExerciseId)
