package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.*
import java.time.LocalDate

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class TrainingPlan(
        private val name: String,
        private val athleticPreparationId: String,
        private val purpose: Purpose,
        private var periodOfTraining: PeriodOfTraining) {

    private var workouts: List<Workout> = emptyList()

    init {
        if (athleticPreparationId.isEmpty()) {
            throw TrainingPlanMustBelongToAthleticPreparation()
        }
        if (name.isEmpty()) {
            throw NameMustNotBeEmpty()
        }
    }

    fun extendTrainingPeriodEnd(end: LocalDate) {
        if (end.isBefore(periodOfTraining.end)) {
            throw PeriodExtensionCannotEndBeforeCurrentPeriod()
        }
        periodOfTraining = PeriodOfTraining(periodOfTraining.beginning, end)
    }

    fun scheduleWorkout(workout: Workout) {
        if (isWorkoutOutOfPeriod(workout)) {
            throw WorkoutMustBeScheduledDuringPeriodOfTraining()
        }
        if (existsOtherWorkoutOnSameDateAndTime(workout)) {
            throw WorkoutOnSameDateTimeAlreadyExists()
        }
        workouts = workouts + workout
    }

    private fun isWorkoutOutOfPeriod(workout: Workout): Boolean {
        val workoutDay = workout.snapshot().day
        return workoutDay.isBefore(periodOfTraining.beginning) ||
                workoutDay.isAfter(periodOfTraining.end)
    }

    private fun existsOtherWorkoutOnSameDateAndTime(workout: Workout): Boolean {
        val workoutSnapshot = workout.snapshot()
        return workouts.any {
            val snapshot = it.snapshot()
            snapshot.day == workoutSnapshot.day && snapshot.time == workoutSnapshot.time
        }
    }

    override fun toString(): String {
        return "TrainingPlan(" +
                "name='$name', " +
                "athleticPreparationId='$athleticPreparationId', " +
                "periodOfTraining=$periodOfTraining, " +
                "purpose=$purpose, " +
                "workouts=$workouts)"
    }

}