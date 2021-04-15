package it.unibo.lss.fcla.athletictraining.usecase.fclat1

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.IncrementalGenerator
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.service.OverlappingAthleticTrainingsCheckerImpl
import it.unibo.lss.fcla.athletictraining.infrastructure.service.MemberProfileUpdaterImpl
import it.unibo.lss.fcla.athletictraining.usecase.DummyPlanAthleticTrainingPresenter
import java.time.LocalDate

/**
 * Test of the [Fclat1PlanAthleticTraining] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat1PlanAthleticTrainingTest : FreeSpec({

    val activeAthleticTrainingRepository = InMemoryActiveAthleticTrainingRepository()
    val idGenerator = IncrementalGenerator()
    val memberProfileUpdater = MemberProfileUpdaterImpl()
    val overlappingAthleticTrainingsChecker = OverlappingAthleticTrainingsCheckerImpl()
    val useCase = Fclat1PlanAthleticTraining(
        DummyPlanAthleticTrainingPresenter(),
        idGenerator,
        memberProfileUpdater,
        activeAthleticTrainingRepository,
        overlappingAthleticTrainingsChecker,
    )

    "The use case FCLAT-1 should" - {
        "allow the planning of an athletic training" - {
            val memberId = MemberId("1234")
            val request = PlanActiveAthleticTrainingRequest(
                AthleticTrainerId("1234"),
                memberId,
                "Purpose test",
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
            )
            useCase.execute(request)
            activeAthleticTrainingRepository.findAllByMemberId(memberId).shouldHaveSize(1)
        }
    }
})
