package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.AthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.AthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.PeriodOfPreparation
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

    override fun findAllByPeriodOfPreparation(periodOfPreparation: PeriodOfPreparation): List<AthleticTraining> {
        return inMemoryStorage.values.filter { it.snapshot().periodOfPreparation == periodOfPreparation }
    }
}
