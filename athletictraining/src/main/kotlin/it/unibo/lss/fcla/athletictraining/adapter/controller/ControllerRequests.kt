package it.unibo.lss.fcla.athletictraining.adapter.controller

import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Nicola Lasagni on 10/04/2021.
 */
interface ControllerRequest

/**
 *
 */
class ListOfTrainingsControllerRequest(val memberId: String) : ControllerRequest

/**
 *
 */
class ListOfWorkoutsControllerRequest : ControllerRequest

/**
 *
 */
class ListOfExercisesControllerRequest : ControllerRequest

/**
 *
 */
class PlanTrainingControllerRequest(
    val athleticTrainerId: String,
    val memberId: String,
    val purpose: String,
    val startDay: LocalDate,
    val endDay: LocalDate
) : ControllerRequest

/**
 *
 */
class ExtendTrainingPeriodControllerRequest(
    val trainingId: String,
    val endDay: LocalDate
) : ControllerRequest

/**
 *
 */
class CompleteTrainingControllerRequest(val trainingId: String) : ControllerRequest

/**
 *
 */
class ScheduleWorkoutControllerRequest(
    val trainingId: String,
    val workoutId: String,
    val day: LocalDate,
    val time: LocalTime
) : ControllerRequest

/**
 *
 */
class RescheduleWorkoutControllerRequest(
    val trainingId: String,
    val scheduledWorkoutId: String,
    val currentDay: LocalDate,
    val currentTime: LocalTime,
    val newDay: LocalDate,
    val newTime: LocalTime
) : ControllerRequest

/**
 *
 */
class CancelScheduleControllerRequest(
    val trainingId: String,
    val workoutId: String,
    val day: LocalDate,
    val time: LocalTime
) : ControllerRequest

/**
 *
 */
class BuildWorkoutControllerRequest(val name: String) : ControllerRequest

/**
 *
 */
class ChooseExerciseControllerRequest(val workoutId: String, val exerciseId: String) : ControllerRequest

/**
 *
 */
class RemoveExerciseControllerRequest(val workoutId: String, val exerciseIndex: Int) : ControllerRequest

/**
 *
 */
class CreateExerciseControllerRequest(
    val gymMachine: String,
    val name: String,
    val durationOfExecution: Int,
    val durationOfRest: Int,
    val intensity: Int,
    val distance: Int
) : ControllerRequest

/**
 *
 */
class DeleteExerciseControllerRequest(val exerciseId: String) : ControllerRequest
