/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat8

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ExerciseNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * Choose an exercise for a workout, refers to requirement FCLAT-8.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
class Fclat8ChooseExerciseForWorkout(
    useCaseOutput: ChooseExerciseForWorkoutOutput,
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : ChooseExerciseForWorkoutInput,
    AthleticTrainingManagement<ChooseExerciseRequest, Workout>(useCaseOutput) {

    override fun processRequest(request: ChooseExerciseRequest): Workout {
        val workoutSnapshot = workoutRepository.findById(WorkoutId(request.workoutId))
            ?: throw WorkoutNotFound()
        val exerciseSnapshot = exerciseRepository.findById(ExerciseId(request.exerciseId))
            ?: throw ExerciseNotFound()
        val workout = Workout.rehydrate(workoutSnapshot)
        workout.prepareExercise(exerciseSnapshot.id)
        workoutRepository.update(workout.snapshot())
        return workout
    }
}
