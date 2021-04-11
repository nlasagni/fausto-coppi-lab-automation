package it.unibo.lss.fcla.athletictraining.adapter.controller

import java.time.LocalDate
import java.time.LocalTime

/**
 * A generic request that can be executed by a [ControllerInput].
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface ControllerRequest

/**
 * A [ControllerRequest] that asks for training list of a member.
 *
 * @property memberId The member whose training are requested.
 */
class ListOfTrainingsControllerRequest(val memberId: String) : ControllerRequest

/**
 * A [ControllerRequest] that asks for all the workouts.
 */
class ListOfWorkoutsControllerRequest : ControllerRequest

/**
 * A [ControllerRequest] that asks for all the exercises.
 */
class ListOfExercisesControllerRequest : ControllerRequest

/**
 * A [ControllerRequest] that asks for planning a training.
 *
 * @property athleticTrainerId The athletic trainer that is planning the training.
 * @property memberId The member for whom training is being planned.
 * @property purpose The purpose of the training.
 * @property startDay The start day of the training.
 * @property endDay The end day of the training.
 */
class PlanTrainingControllerRequest(
    val athleticTrainerId: String,
    val memberId: String,
    val purpose: String,
    val startDay: LocalDate,
    val endDay: LocalDate
) : ControllerRequest

/**
 * A [ControllerRequest] that asks for extending a training period.
 *
 * @property trainingId The training whose period will be extended.
 * @property newEndDay The new end day of the training.
 */
class ExtendTrainingPeriodControllerRequest(
    val trainingId: String,
    val newEndDay: LocalDate
) : ControllerRequest

/**
 * A [ControllerRequest] that asks for training completion.
 *
 * @property trainingId The training that is going to be completed.
 */
class CompleteTrainingControllerRequest(val trainingId: String) : ControllerRequest

/**
 * A [ControllerRequest] that asks for scheduling a workout.
 *
 * @property trainingId The training during which the workout will be scheduled.
 * @property workoutId The workout that is going to be scheduled.
 * @property day The desired day of scheduling.
 * @property time The desired time of scheduling.
 */
class ScheduleWorkoutControllerRequest(
    val trainingId: String,
    val workoutId: String,
    val day: LocalDate,
    val time: LocalTime
) : ControllerRequest

/**
 * A [ControllerRequest] that asks for rescheduling a workout.
 *
 * @property trainingId The training during which the workout will be rescheduled.
 * @property workoutId The workout that is going to be rescheduled.
 * @property currentDay The day of the current scheduling.
 * @property currentTime The time of the current scheduling.
 * @property newDay The desired new day of scheduling.
 * @property newTime The desired new time of scheduling.
 */
class RescheduleWorkoutControllerRequest(
    val trainingId: String,
    val workoutId: String,
    val currentDay: LocalDate,
    val currentTime: LocalTime,
    val newDay: LocalDate,
    val newTime: LocalTime
) : ControllerRequest

/**
 * A [ControllerRequest] that asks for cancel the scheduling of a workout.
 *
 * @property trainingId The training of which the workout will be canceled.
 * @property workoutId The workout that is going to be canceled.
 * @property day The day of the current scheduling.
 * @property time The time of the current scheduling.
 */
class CancelScheduleControllerRequest(
    val trainingId: String,
    val workoutId: String,
    val day: LocalDate,
    val time: LocalTime
) : ControllerRequest

/**
 * A [ControllerRequest] that asks for building a workout.
 *
 * @property name The name of the workout.
 */
class BuildWorkoutControllerRequest(val name: String) : ControllerRequest

/**
 * A [ControllerRequest] that asks for choosing an exercise for a workout.
 *
 * @property workoutId The id of the workout for which the exercise was chosen.
 * @property exerciseId The id of the exercise chosen.
 */
class ChooseExerciseControllerRequest(val workoutId: String, val exerciseId: String) : ControllerRequest

/**
 * A [ControllerRequest] that asks for choosing an exercise for a workout.
 *
 * @property workoutId The id of the workout for which the exercise was chosen.
 * @property exerciseIndex The index of the exercise that must be removed from the workout.
 */
class RemoveExerciseControllerRequest(val workoutId: String, val exerciseIndex: Int) : ControllerRequest

/**
 * A [ControllerRequest] that asks for creating an exercise.
 *
 * @property gymMachine The gym machine to which exercise refers.
 * @property name The name of the exercise.
 * @property durationOfExecution The duration of execution of the exercise, expressed in seconds.
 * @property durationOfRest The duration of rest of the exercise, expressed in seconds.
 * @property intensity The intensity of the exercise.
 * @property distance The distance of the exercise.
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
