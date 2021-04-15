package it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose

/**
 * A snapshot class used to expose the relevant information about an [ActiveAthleticTraining].
 *
 * @property id The [ActiveAthleticTrainingId] to which this snapshot refers.
 * @property athleticTrainer The [AthleticTrainerId] of the [ActiveAthleticTraining] to which this snapshot refers.
 * @property member The [MemberId] of the [ActiveAthleticTraining] to which this snapshot refers.
 * @property purpose The [Purpose] of the [ActiveAthleticTraining] to which this snapshot refers.
 * @property period The [Period] of the [ActiveAthleticTraining] to which this snapshot refers.
 * @property scheduledWorkouts The snapshot of the workout scheduled during the [ActiveAthleticTraining].
 *
 * @author Nicola Lasagni on 03/03/2021.
 */
data class ActiveAthleticTrainingSnapshot(
    val id: ActiveAthleticTrainingId,
    val athleticTrainer: AthleticTrainerId,
    val member: MemberId,
    val purpose: Purpose,
    val period: Period,
    val scheduledWorkouts: Collection<ScheduledWorkoutSnapshot>
)
