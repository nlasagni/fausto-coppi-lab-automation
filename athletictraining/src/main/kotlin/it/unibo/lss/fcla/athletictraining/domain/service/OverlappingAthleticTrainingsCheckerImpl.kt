package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.shared.Period

/**
 * Concrete implementation of an [OverlappingAthleticTrainingsChecker] service.
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
class OverlappingAthleticTrainingsCheckerImpl : OverlappingAthleticTrainingsChecker {
    override fun existsOverlappingAthleticTraining(
        athleticTrainings: Collection<ActiveAthleticTraining>,
        period: Period
    ): Boolean {
        return athleticTrainings.any { it.overlapsWithPeriod(period) }
    }
}
