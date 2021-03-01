package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveAthleticTrainer
import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveMember
import it.unibo.lss.fcla.athleticpreparation.domain.exception.TrainingPlanMustNotOverlap

/**
 * The AthleticPreparation domain entity.
 *
 * This entity encapsulates all information which belongs to an athletic preparation.
 *
 * @property athleticTrainerId The id reference of the athletic trainer who is planning the athletic preparation.
 * @property memberId The id reference of the member for whom the athletic preparation is being planned.
 * @property periodOfPreparation The period of athletic preparation. See [PeriodOfPreparation].
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class ActiveAthleticPreparation(
        private val athleticTrainerId: String,
        private val memberId: String,
        private val periodOfPreparation: PeriodOfPreparation
) {

    /** The id of the athletic preparation. */
    private val id: String
    private var trainingPlans: List<TrainingPlan> = emptyList()

    init {
        if (athleticTrainerId.isEmpty()) {
            throw AthleticPreparationMustHaveAthleticTrainer()
        }
        if (memberId.isEmpty()) {
            throw AthleticPreparationMustHaveMember()
        }
        id = generateId()
    }

    /**
     * Returns a unique id of the athletic preparation which will be stored
     * into the [id] private property.
     */
    private fun generateId(): String =
            "$athleticTrainerId-$memberId-${periodOfPreparation.beginning.dayOfYear}"

    /**
     *
     */
    fun prepareTrainingPlan(trainingPlan: TrainingPlan) {
        if (trainingPlanOverlaps(trainingPlan)) {
            throw TrainingPlanMustNotOverlap()
        }
        trainingPlans = trainingPlans + trainingPlan
    }

    /**
     *
     */
    private fun trainingPlanOverlaps(trainingPlan: TrainingPlan): Boolean {
        return false
    }

}