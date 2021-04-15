package it.unibo.lss.fcla.athletictraining.usecase.fclat12

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat12CheckActiveAthleticTrainings] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface CheckActiveAthleticTrainingsOutput : UseCaseOutput<Collection<ActiveAthleticTraining>>
