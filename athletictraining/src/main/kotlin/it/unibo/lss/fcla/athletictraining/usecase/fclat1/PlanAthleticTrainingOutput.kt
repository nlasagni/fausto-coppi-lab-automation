package it.unibo.lss.fcla.athletictraining.usecase.fclat1

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.UseCaseOutput

/**
 * The [UseCaseOutput] port of the [Fclat1PlanAthleticTraining] use case.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
interface PlanAthleticTrainingOutput : UseCaseOutput<ActiveAthleticTraining>
