package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.*
import java.time.LocalDate

/**
 * A TrainingPlan is a plan of [Workout] managed by an athletic trainer that a member has to follow
 * during a specific period period in order to reach his/her training purpose.
 *
 * A TrainingPlan must belong to an [AthleticPreparation], and have a name.
 *
 * During a TrainingPlan it is not possible to have two TrainingPlan
 * in the same PeriodOfTraining.
 *
 * The lifecycle of a TrainingPlan ends when its [PeriodOfTraining] ends, or when
 * the related [AthleticPreparation] is completed.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class TrainingPlan(
        private val name: String,
        private val athleticPreparationId: String,
        private val purpose: Purpose,
        private var periodOfTraining: PeriodOfTraining
) {

    private var workouts: List<Workout> = emptyList()

    init {
        if (athleticPreparationId.isEmpty()) {
            throw TrainingPlanMustBelongToAthleticPreparation()
        }
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
    }

    /**
     * Generates a [TrainingPlanSnapshot] with the information about this TrainingPlan.
     */
    fun snapshot() = TrainingPlanSnapshot(
            name,
            athleticPreparationId,
            purpose,
            periodOfTraining,
            workouts,
    )

    /**
     * Extends the [periodOfTraining] end.
     * If the [end] parameter is a date before the current end,
     * throws a [PeriodExtensionCannotEndBeforeCurrentPeriod].
     */
    fun extendTrainingPeriodEnd(end: LocalDate) {
        if (end.isBefore(periodOfTraining.end)) {
            throw PeriodExtensionCannotEndBeforeCurrentPeriod()
        }
        periodOfTraining = PeriodOfTraining(periodOfTraining.beginning, end)
    }

    /**
     * Schedules a [workout] for this TrainingPlan.
     * The workout must not be out of the [periodOfTraining], otherwise
     * a [WorkoutMustBeScheduledDuringPeriodOfTraining] exception will be thrown.
     * If another [Workout] in the same date and time already exists, a
     * [WorkoutOnSameDateTimeAlreadyExists] exception will be thrown.
     */
    fun scheduleWorkout(workout: Workout) {
        if (isWorkoutOutOfPeriod(workout)) {
            throw WorkoutMustBeScheduledDuringPeriodOfTraining()
        }
        if (existsOtherWorkoutOnSameDateAndTime(workout)) {
            throw WorkoutOnSameDateTimeAlreadyExists()
        }
        workouts = workouts + workout
    }

    /**
     * Checks if the [workout] that is going to be scheduled is out of the
     * [periodOfTraining].
     */
    private fun isWorkoutOutOfPeriod(workout: Workout): Boolean {
        val workoutDay = workout.snapshot().day
        return workoutDay.isBefore(periodOfTraining.beginning) ||
                workoutDay.isAfter(periodOfTraining.end)
    }

    /**
     * Checks if exists another [Workout] with same date and time of the
     * [workout] that is going to be scheduled.
     */
    private fun existsOtherWorkoutOnSameDateAndTime(workout: Workout): Boolean {
        val workoutSnapshot = workout.snapshot()
        return workouts.any {
            val snapshot = it.snapshot()
            snapshot.day == workoutSnapshot.day && snapshot.time == workoutSnapshot.time
        }
    }

}