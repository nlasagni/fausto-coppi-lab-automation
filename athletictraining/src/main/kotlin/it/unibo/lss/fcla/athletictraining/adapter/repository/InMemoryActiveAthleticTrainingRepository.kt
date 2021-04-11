package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository

/**
 * An [ActiveAthleticTrainingRepository] that stores information in application memory.
 *
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryActiveAthleticTrainingRepository : ActiveAthleticTrainingRepository {

    private val inMemoryStorage: MutableMap<ActiveAthleticTrainingId, ActiveAthleticTraining> =
        mutableMapOf()

    override fun add(activeAthleticTraining: ActiveAthleticTraining): ActiveAthleticTraining {
        return addOrUpdate(activeAthleticTraining)
    }

    override fun update(activeAthleticTraining: ActiveAthleticTraining): ActiveAthleticTraining {
        return addOrUpdate(activeAthleticTraining)
    }

    override fun remove(activeAthleticTraining: ActiveAthleticTraining): Boolean {
        return inMemoryStorage.remove(activeAthleticTraining.id) != null
    }

    private fun addOrUpdate(activeAthleticTraining: ActiveAthleticTraining): ActiveAthleticTraining {
        val id = activeAthleticTraining.id
        inMemoryStorage[id] = activeAthleticTraining
        return activeAthleticTraining
    }

    override fun findById(id: ActiveAthleticTrainingId): ActiveAthleticTraining? = inMemoryStorage[id]

    override fun findAllByMemberId(memberId: MemberId): List<ActiveAthleticTraining> {
        return inMemoryStorage.values.filter { it.member == memberId }
    }
}
