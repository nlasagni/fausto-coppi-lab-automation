/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat5

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.service.GymOpenChecker
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.domain.service.WorkoutTotalDurationCalculator
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.ActiveAthleticTrainingNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.GymClosedAtSchedule
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.WorkoutNotFound
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * Reschedules a workout for an athletic training, refers to requirement FCLAT-5.
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class Fclat5RescheduleWorkout(
    useCaseOutput: RescheduleWorkoutOutput,
    private val gymOpenChecker: GymOpenChecker,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val workoutTotalDurationCalculator: WorkoutTotalDurationCalculator,
    private val repository: ActiveAthleticTrainingRepository,
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
) : RescheduleWorkoutInput,
    AthleticTrainingManagement<RescheduleWorkoutRequest, ActiveAthleticTraining>(useCaseOutput) {

    override fun processRequest(request: RescheduleWorkoutRequest): ActiveAthleticTraining {

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
        val oldSchedule = Schedule(
            request.currentDay,
            request.currentTime,
            request.currentTime.plusSeconds(workoutDuration.seconds)
        )
        val newSchedule = Schedule(
            request.newDay,
            request.newTime,
            request.newTime.plusSeconds(workoutDuration.seconds)
        )
        if (!gymOpenChecker.isGymOpenForSchedule(newSchedule)) {
            throw throw GymClosedAtSchedule()
        }
        val activeAthleticTrainingSnapshot =
            repository.findById(ActiveAthleticTrainingId(request.activeAthleticTrainingId))
                ?: throw ActiveAthleticTrainingNotFound()
        val activeAthleticTraining = ActiveAthleticTraining.rehydrate(activeAthleticTrainingSnapshot)
        activeAthleticTraining.rescheduleWorkout(
            WorkoutId(request.workoutId),
            oldSchedule,
            newSchedule
        )
        memberProfileUpdater.updateProfile(activeAthleticTraining.member)
        repository.update(activeAthleticTraining.snapshot())
        return activeAthleticTraining
    }
}
