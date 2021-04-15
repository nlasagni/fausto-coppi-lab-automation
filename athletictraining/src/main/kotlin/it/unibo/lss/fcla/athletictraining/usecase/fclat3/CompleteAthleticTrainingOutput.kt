package it.unibo.lss.fcla.athletictraining.usecase.fclat3

import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat3CompleteAthleticTraining] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface CompleteAthleticTrainingOutput : UseCaseOutput<CompletedAthleticTraining>
