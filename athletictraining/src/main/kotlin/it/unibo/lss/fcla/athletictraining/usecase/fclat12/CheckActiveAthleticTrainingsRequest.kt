package it.unibo.lss.fcla.athletictraining.usecase.fclat12

import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId

/**
 * Class that represents the request coming from outer layer of
 * checking all the active athletic trainings.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
data class CheckActiveAthleticTrainingsRequest(val memberId: String)
