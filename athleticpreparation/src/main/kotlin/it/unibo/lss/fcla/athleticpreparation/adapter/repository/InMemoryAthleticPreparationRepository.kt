package it.unibo.lss.fcla.athleticpreparation.adapter.repository

import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticPreparation
import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticPreparationId
import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticTrainerId
import it.unibo.lss.fcla.athleticpreparation.domain.model.MemberId
import it.unibo.lss.fcla.athleticpreparation.domain.model.PeriodOfPreparation
import it.unibo.lss.fcla.athleticpreparation.usecase.port.AthleticPreparationRepository

/**
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryAthleticPreparationRepository : AthleticPreparationRepository {

    private val inMemoryStorage: MutableMap<AthleticPreparationId, AthleticPreparation> = mutableMapOf()

    override fun add(athleticPreparation: AthleticPreparation): AthleticPreparation {
        return addOrUpdate(athleticPreparation)
    }

    override fun update(athleticPreparation: AthleticPreparation): AthleticPreparation {
        return addOrUpdate(athleticPreparation)
    }

    private fun addOrUpdate(athleticPreparation: AthleticPreparation): AthleticPreparation {
        val id = athleticPreparation.snapshot().id
        inMemoryStorage[id] = athleticPreparation
        return athleticPreparation
    }

    override fun findById(id: AthleticPreparationId): AthleticPreparation? = inMemoryStorage[id]

    override fun findAllByAthleticTrainerId(athleticTrainerId: AthleticTrainerId): List<AthleticPreparation> {
        return inMemoryStorage.values.filter { it.snapshot().athleticTrainerId == athleticTrainerId }
    }

    override fun findAllByMemberId(memberId: MemberId): List<AthleticPreparation> {
        return inMemoryStorage.values.filter { it.snapshot().memberId == memberId }
    }

    override fun findAllByPeriodOfPreparation(periodOfPreparation: PeriodOfPreparation): List<AthleticPreparation> {
        return inMemoryStorage.values.filter { it.snapshot().periodOfPreparation == periodOfPreparation }
    }
}
