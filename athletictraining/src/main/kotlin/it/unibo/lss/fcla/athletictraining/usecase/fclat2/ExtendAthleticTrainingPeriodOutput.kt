package it.unibo.lss.fcla.athletictraining.usecase.fclat2

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat2ExtendAthleticTrainingPeriod] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface ExtendAthleticTrainingPeriodOutput : UseCaseOutput<ActiveAthleticTraining>
