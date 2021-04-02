package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

/**
 * A snapshot class used to expose all the information about an [AthleticTraining].
 *
 * @author Nicola Lasagni on 03/03/2021.
 */
data class AthleticTrainingSnapshot(
    val id: AthleticTrainingId,
    val athleticTrainerId: AthleticTrainerId,
    val memberId: MemberId,
    val period: Period,
    val scheduledWorkout: List<ScheduledWorkout>
)