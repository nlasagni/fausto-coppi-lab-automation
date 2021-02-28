package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.TrainingPlanMustBelongToAthleticPreparation

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class TrainingPlan(
        private val athleticPreparationId: String,
        private val periodOfTraining: PeriodOfTraining,
        private val purpose: Purpose) {

    private var workouts: List<Workout> = emptyList()

    init {
        if (athleticPreparationId.isEmpty()) {
            throw TrainingPlanMustBelongToAthleticPreparation()
        }
    }

    fun extendTrainingPeriod(periodOfTraining: PeriodOfTraining) {

    }

    fun prepareWorkout(workout: Workout) {

    }

}