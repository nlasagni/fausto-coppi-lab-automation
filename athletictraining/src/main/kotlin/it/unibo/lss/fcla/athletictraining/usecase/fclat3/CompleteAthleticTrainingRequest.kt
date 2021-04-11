package it.unibo.lss.fcla.athletictraining.usecase.fclat3

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId

/**
 * Class that represents the request coming from outer layer of
 * athletic training completion.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class CompleteAthleticTrainingRequest(val id: ActiveAthleticTrainingId)
