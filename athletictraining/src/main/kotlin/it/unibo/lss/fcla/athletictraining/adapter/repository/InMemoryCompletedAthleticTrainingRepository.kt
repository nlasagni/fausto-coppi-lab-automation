package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.CompletedAthleticTrainingRepository

/**
 * A [CompletedAthleticTrainingRepository] that stores information in application memory.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class InMemoryCompletedAthleticTrainingRepository : CompletedAthleticTrainingRepository {

    private val inMemoryStorage: MutableMap<CompletedAthleticTrainingId, CompletedAthleticTraining> =
        mutableMapOf()

    override fun add(completedAthleticTraining: CompletedAthleticTraining): CompletedAthleticTraining {
        inMemoryStorage[completedAthleticTraining.id] = completedAthleticTraining
        return completedAthleticTraining
    }
}
