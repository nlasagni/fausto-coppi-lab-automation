package it.unibo.lss.fcla.athletictraining.adapter.controller

import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.usecase.fclat1.PlanActiveAthleticTrainingRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat1.PlanAthleticTrainingInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat12.CheckActiveAthleticTrainingsInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat12.CheckActiveAthleticTrainingsRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat2.ExtendAthleticTrainingPeriodInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat2.ExtendAthleticTrainingPeriodRequest
import it.unibo.lss.fcla.athletictraining.usecase.fclat3.CompleteAthleticTrainingInput
import it.unibo.lss.fcla.athletictraining.usecase.fclat3.CompleteAthleticTrainingRequest

/**
 * @author Nicola Lasagni on 10/04/2021.
 */
class AthleticTrainingController(
    private val planTrainingInput: PlanAthleticTrainingInput,
    private val extendTrainingPeriodInput: ExtendAthleticTrainingPeriodInput,
    private val completeTrainingInput: CompleteAthleticTrainingInput,
    private val checkActiveAthleticTrainingsInput: CheckActiveAthleticTrainingsInput
) : ControllerInput {

    override fun executeRequest(request: ControllerRequest) {
        when (request) {
            is PlanTrainingControllerRequest -> planTrainingInput.execute(
                PlanActiveAthleticTrainingRequest(
                    AthleticTrainerId(request.athleticTrainerId),
                    MemberId(request.memberId),
                    request.purpose,
                    request.startDay,
                    request.endDay
                )
            )
            is ExtendTrainingPeriodControllerRequest -> extendTrainingPeriodInput.execute(
                ExtendAthleticTrainingPeriodRequest(
                    request.trainingId,
                    request.endDay
                )
            )
            is CompleteTrainingControllerRequest -> completeTrainingInput.execute(
                CompleteAthleticTrainingRequest(
                    request.trainingId
                )
            )
            is ListOfTrainingsControllerRequest -> checkActiveAthleticTrainingsInput.execute(
                CheckActiveAthleticTrainingsRequest(request.memberId)
            )
        }
    }
}
