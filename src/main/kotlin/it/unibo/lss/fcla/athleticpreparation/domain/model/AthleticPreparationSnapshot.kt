package it.unibo.lss.fcla.athleticpreparation.domain.model

/**
 * A snapshot class used to expose all the information about an [AthleticPreparation].
 *
 * @author Nicola Lasagni on 03/03/2021.
 */
data class AthleticPreparationSnapshot(
    val id: AthleticPreparationId,
    val athleticTrainerId: AthleticTrainerId,
    val memberId: MemberId,
    val periodOfPreparation: PeriodOfPreparation,
    val trainingPlans: List<TrainingPlan>
)
