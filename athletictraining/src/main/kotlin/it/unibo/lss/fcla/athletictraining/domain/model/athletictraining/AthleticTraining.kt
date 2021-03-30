package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingAlreadyCompleted
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveMember
import it.unibo.lss.fcla.athletictraining.domain.exception.TrainingPlanMustBePreparedDuringPeriodOfPreparation
import it.unibo.lss.fcla.athletictraining.domain.exception.TrainingPlanMustNotOverlap
import it.unibo.lss.fcla.athletictraining.domain.model.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.TrainingPlan

/**
 * This is one of the main entities of the Athletic Preparation Bounded Context.
 *
 * An AthleticPreparation must be prepared by an athletic trainer and for a member.
 * It is identified by an auto-generated id based on the athleticTrainerId,
 * memberId and periodOfPreparation properties.
 *
 * The purpose of an AthleticPreparation is to organize the training plans
 * which will bring the member to reach his/her athletic objectives.
 *
 * During a member's AthleticPreparation it is not possible to have two TrainingPlan
 * in the same PeriodOfTraining.
 *
 * The lifecycle of an AthleticPreparation ends when the athletic trainer
 * decides that it is completed.
 *
 * @property athleticTrainerId The id reference of the athletic trainer who is preparing the athletic training.
 * @property memberId The id reference of the member for whom the athletic training is being prepared.
 * @property periodOfPreparation The period of athletic traning. See [PeriodOfPreparation].
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class AthleticTraining(
    private val athleticTrainerId: AthleticTrainerId,
    private val memberId: MemberId,
    private val periodOfPreparation: PeriodOfPreparation
) {

    private enum class Status {
        ACTIVE, COMPLETED
    }

    private val id: AthleticTrainingId
    private var trainingPlans: List<TrainingPlan> = emptyList()
    private var status: Status = Status.ACTIVE

    init {
        if (athleticTrainerId.value.isEmpty()) {
            throw AthleticTrainingMustHaveAthleticTrainer()
        }
        if (memberId.value.isEmpty()) {
            throw AthleticTrainingMustHaveMember()
        }
        id = generateId()
    }

    /**
     * Returns a unique id of this AthleticPreparation which will be stored
     * into the [id] private property.
     */
    private fun generateId(): AthleticTrainingId =
        AthleticTrainingId("$athleticTrainerId-$memberId-${periodOfPreparation.beginning.dayOfYear}")

    /**
     * Prepares a [TrainingPlan] for this AthleticPreparation.
     * Throws [AthleticTrainingAlreadyCompleted] if there's an attempt to prepare a TrainingPlan
     * for an already-completed AthleticPreparation.
     * Throws [TrainingPlanMustBePreparedDuringPeriodOfPreparation] if the [trainingPlan] that is
     * going to be prepared has a period that is not included by the [periodOfPreparation] of this athletic
     * training.
     * Throws [TrainingPlanMustNotOverlap] if the [trainingPlan] that is going to be prepared overlaps
     * with an already-planned TrainingPlan.
     */
    fun prepareTrainingPlan(trainingPlan: TrainingPlan) {
        if (isAlreadyCompleted()) {
            throw AthleticTrainingAlreadyCompleted()
        }
        if (isTrainingPlanOutOfPeriod(trainingPlan)) {
            throw TrainingPlanMustBePreparedDuringPeriodOfPreparation()
        }
        if (trainingPlanOverlaps(trainingPlan)) {
            throw TrainingPlanMustNotOverlap()
        }
        trainingPlans = trainingPlans + trainingPlan
    }

    /**
     * Checks if the [trainingPlan] that is going to be scheduled is out of the
     * [periodOfPreparation].
     */
    private fun isTrainingPlanOutOfPeriod(trainingPlan: TrainingPlan): Boolean {
        val trainingPlanSnapshot = trainingPlan.snapshot()
        val trainingPlanPeriodBeginning = trainingPlanSnapshot.periodOfTraining.beginning
        val trainingPlanPeriodEnd = trainingPlanSnapshot.periodOfTraining.end
        return trainingPlanPeriodBeginning.isBefore(periodOfPreparation.beginning) ||
            trainingPlanPeriodEnd.isAfter(periodOfPreparation.end)
    }

    /**
     * Checks if the [trainingPlan] that is going to be prepared overlaps
     * with another that has already been planned.
     */
    private fun trainingPlanOverlaps(trainingPlan: TrainingPlan): Boolean {
        val trainingPlanSnapshot = trainingPlan.snapshot()
        return trainingPlans.any {
            val snapshot = it.snapshot()
            snapshot.periodOfTraining.beginning.isBefore(trainingPlanSnapshot.periodOfTraining.end) &&
                snapshot.periodOfTraining.end.isAfter(trainingPlanSnapshot.periodOfTraining.beginning)
        }
    }

    /**
     * Completes this AthleticPreparation.
     */
    fun complete() {
        status = Status.COMPLETED
    }

    /**
     * Checks if this AthleticPreparation is completed.
     */
    private fun isAlreadyCompleted() = status == Status.COMPLETED

    /**
     * Generates an [AthleticTrainingSnapshot] with the information about this AthleticPreparation.
     */
    fun snapshot() = AthleticTrainingSnapshot(
        id,
        athleticTrainerId,
        memberId,
        periodOfPreparation,
        trainingPlans
    )
}
