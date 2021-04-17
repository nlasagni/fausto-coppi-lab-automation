/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat6

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.domain.service.WorkoutTotalDurationCalculator
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * Cancels a workout previously scheduled for an athletic training, refers to requirement FCLAT-6.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
class Fclat6CancelScheduledWorkout(
    useCaseOutput: CancelScheduledWorkoutOutput,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val workoutTotalDurationCalculator: WorkoutTotalDurationCalculator,
    private val activeAthleticTrainingRepository: ActiveAthleticTrainingRepository,
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : CancelScheduledWorkoutInput,
    AthleticTrainingManagement<CancelScheduledWorkoutRequest, Boolean>(useCaseOutput) {

    override fun processRequest(request: CancelScheduledWorkoutRequest): Boolean {
        val activeAthleticTrainingSnapshot = activeAthleticTrainingRepository.findById(
            ActiveAthleticTrainingId(request.activeAthleticTrainingId)
        ) ?: throw ActiveAthleticTrainingNotFound()
        val workoutId = WorkoutId(request.workoutId)
        val workoutSnapshot = workoutRepository.findById(workoutId)
            ?: throw WorkoutNotFound()
        val exerciseIds = workoutSnapshot.exercises
        val exercises = mutableListOf<Exercise>()
        for (exerciseId in exerciseIds) {
            val exerciseSnapshot = exerciseRepository.findById(exerciseId)
            if (exerciseSnapshot != null) {
                exercises.add(Exercise.rehydrate(exerciseSnapshot))
            }
        }
        val workoutDuration = workoutTotalDurationCalculator.computeTotalDuration(exercises)
        val schedule = Schedule(request.day, request.time, request.time.plusSeconds(workoutDuration.seconds))
        val activeAthleticTraining = ActiveAthleticTraining.rehydrate(activeAthleticTrainingSnapshot)
        activeAthleticTraining.cancelScheduledWorkout(workoutId, schedule)
        memberProfileUpdater.updateProfile(activeAthleticTraining.member)
        activeAthleticTrainingRepository.update(activeAthleticTraining.snapshot())
        return true
    }
}
