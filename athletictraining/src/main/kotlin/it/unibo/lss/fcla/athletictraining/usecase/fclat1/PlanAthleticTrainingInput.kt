package it.unibo.lss.fcla.athletictraining.usecase.fclat1

import it.unibo.lss.fcla.athletictraining.usecase.shared.input.UseCaseInput

/**
 * The [UseCaseInput] port of the [Fclat1PlanAthleticTraining] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
interface PlanAthleticTrainingInput : UseCaseInput<PlanActiveAthleticTrainingRequest>
