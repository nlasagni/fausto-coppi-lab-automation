package it.unibo.lss.fcla.athletictraining.usecase.fclat2

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.service.OverlappingAthleticTrainingsCheckerImpl
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.infrastructure.service.MemberProfileUpdaterImpl
import it.unibo.lss.fcla.athletictraining.usecase.DummyExtendAthleticTrainingPeriodPresenter
import java.time.LocalDate

/**
 * Test of the [Fclat2ExtendAthleticTrainingPeriod] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat2ExtendAthleticTrainingPeriodTest : FreeSpec({

    val activeAthleticTrainingId = ActiveAthleticTrainingId("1234")
    val athleticTrainer = AthleticTrainerId("1234")
    val member = MemberId("1234")
    val beginning = LocalDate.now()
    val end = beginning.plusMonths(1)
    val period = Period(beginning, end)
    val purpose = Purpose("Athletic Preparation")
    val activeAthleticTraining = ActiveAthleticTraining(
        activeAthleticTrainingId,
        athleticTrainer,
        member,
        purpose,
        period
    )

    val activeAthleticTrainingRepository = InMemoryActiveAthleticTrainingRepository()
    val memberProfileUpdater = MemberProfileUpdaterImpl()
    val overlappingAthleticTrainingsChecker = OverlappingAthleticTrainingsCheckerImpl()
    val useCase = Fclat2ExtendAthleticTrainingPeriod(
        DummyExtendAthleticTrainingPeriodPresenter(),
        memberProfileUpdater,
        activeAthleticTrainingRepository,
        overlappingAthleticTrainingsChecker,
    )

    "The use case FCLAT-2 should" - {
        "extend the period of an athletic training" - {
            activeAthleticTrainingRepository.add(activeAthleticTraining.snapshot())
            val newEnd = end.plusMonths(1)
            val request = ExtendAthleticTrainingPeriodRequest(
                activeAthleticTrainingId.value,
                newEnd
            )
            useCase.execute(request)
            activeAthleticTrainingRepository
                .findById(activeAthleticTrainingId)
                ?.period
                ?.endDay()
                .shouldBe(newEnd)
        }
    }
})
