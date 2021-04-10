package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.shared.Period

/**
 * Domain service that checks if there are overlapping athletic trainings
 * in a given period.
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
interface OverlappingAthleticTrainingsChecker {

    /**
     * Checks if there is at least one athletic training that overlaps with the
     * specified [period].
     */
    fun existsOverlappingAthleticTraining(
        athleticTrainings: Collection<ActiveAthleticTraining>,
        period: Period
    ): Boolean

}