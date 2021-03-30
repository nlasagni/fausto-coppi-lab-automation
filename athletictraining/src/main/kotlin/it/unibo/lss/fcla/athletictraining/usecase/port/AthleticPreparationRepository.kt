package it.unibo.lss.fcla.athletictraining.usecase.port

import it.unibo.lss.fcla.athletictraining.domain.model.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.AthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.AthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.PeriodOfPreparation

/**
 * @author Nicola Lasagni on 06/03/2021.
 */
interface AthleticPreparationRepository {

    fun add(athleticTraining: AthleticTraining): AthleticTraining

    fun update(athleticTraining: AthleticTraining): AthleticTraining

    fun findById(id: AthleticTrainingId): AthleticTraining?

    fun findAllByAthleticTrainerId(athleticTrainerId: AthleticTrainerId): List<AthleticTraining>

    fun findAllByMemberId(memberId: MemberId): List<AthleticTraining>

    fun findAllByPeriodOfPreparation(periodOfPreparation: PeriodOfPreparation): List<AthleticTraining>
}
