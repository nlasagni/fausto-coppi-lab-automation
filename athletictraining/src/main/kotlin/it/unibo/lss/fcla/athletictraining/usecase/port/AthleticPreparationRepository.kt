package it.unibo.lss.fcla.athletictraining.usecase.port

import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.Period
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId

/**
 * @author Nicola Lasagni on 06/03/2021.
 */
interface AthleticPreparationRepository {

    fun add(athleticTraining: AthleticTraining): AthleticTraining

    fun update(athleticTraining: AthleticTraining): AthleticTraining

    fun findById(id: AthleticTrainingId): AthleticTraining?

    fun findAllByAthleticTrainerId(athleticTrainerId: AthleticTrainerId): List<AthleticTraining>

    fun findAllByMemberId(memberId: MemberId): List<AthleticTraining>

    fun findAllByPeriodOfPreparation(period: Period): List<AthleticTraining>
}
