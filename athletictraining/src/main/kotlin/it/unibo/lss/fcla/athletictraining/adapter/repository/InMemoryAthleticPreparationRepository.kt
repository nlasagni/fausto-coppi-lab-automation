package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.Period
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.usecase.port.AthleticPreparationRepository

/**
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryAthleticPreparationRepository : AthleticPreparationRepository {

    private val inMemoryStorage: MutableMap<AthleticTrainingId, AthleticTraining> = mutableMapOf()

    override fun add(athleticTraining: AthleticTraining): AthleticTraining {
        return addOrUpdate(athleticTraining)
    }

    override fun update(athleticTraining: AthleticTraining): AthleticTraining {
        return addOrUpdate(athleticTraining)
    }

    private fun addOrUpdate(athleticTraining: AthleticTraining): AthleticTraining {
        val id = athleticTraining.snapshot().id
        inMemoryStorage[id] = athleticTraining
        return athleticTraining
    }

    override fun findById(id: AthleticTrainingId): AthleticTraining? = inMemoryStorage[id]

    override fun findAllByAthleticTrainerId(athleticTrainerId: AthleticTrainerId): List<AthleticTraining> {
        return inMemoryStorage.values.filter { it.snapshot().athleticTrainerId == athleticTrainerId }
    }

    override fun findAllByMemberId(memberId: MemberId): List<AthleticTraining> {
        return inMemoryStorage.values.filter { it.snapshot().memberId == memberId }
    }

    override fun findAllByPeriodOfPreparation(period: Period): List<AthleticTraining> {
        return inMemoryStorage.values.filter { it.snapshot().period == period }
    }
}
