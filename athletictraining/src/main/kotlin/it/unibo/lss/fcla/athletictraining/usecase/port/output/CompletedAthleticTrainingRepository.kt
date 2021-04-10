package it.unibo.lss.fcla.athletictraining.usecase.port.output

import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTraining

/**
 * Repository that refers to the Completed Athletic Training aggregate.
 *
 * @author Nicola Lasagni on 06/04/2021.
 */
interface CompletedAthleticTrainingRepository {

    /**
     * Adds the desired [completedAthleticTraining] to this repository.
     * @return The [CompletedAthleticTraining] just added.
     */
    fun add(completedAthleticTraining: CompletedAthleticTraining): CompletedAthleticTraining
}
