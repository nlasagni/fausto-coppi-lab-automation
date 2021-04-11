package it.unibo.lss.fcla.athletictraining.usecase.fclat1

import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import java.time.LocalDate

/**
 * Class that represents the request coming from outer layer of
 * planning a new athletic training.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class PlanActiveAthleticTrainingRequest(
    val athleticTrainerId: AthleticTrainerId,
    val memberId: MemberId,
    val purpose: String,
    val startDay: LocalDate,
    val endDay: LocalDate
)