package it.unibo.lss.fcla.athletictraining.adapter.repository

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.PeriodOfPreparation
import it.unibo.lss.fcla.athletictraining.usecase.port.AthleticPreparationRepository
import org.junit.jupiter.api.Assertions
import java.time.LocalDate

/**
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryAthleticPreparationRepositoryTest : FreeSpec({

    lateinit var repository: AthleticPreparationRepository
    lateinit var athleticTrainerId: AthleticTrainerId
    lateinit var memberId: MemberId
    lateinit var periodOfPreparation: PeriodOfPreparation
    lateinit var athleticTraining: AthleticTraining

    beforeAny {
        repository = InMemoryAthleticPreparationRepository()
        val now = LocalDate.now()
        athleticTrainerId = AthleticTrainerId("1234")
        memberId = MemberId("1234")
        periodOfPreparation = PeriodOfPreparation(
            now,
            now.plusMonths(PeriodOfPreparation.minimumPeriodDurationInMonth.toLong())
        )
        athleticTraining = AthleticTraining(athleticTrainerId, memberId, periodOfPreparation)
    }

    "An InMemoryAthleticPreparationRepository should" - {
        "be able to persist AthleticPreparation" - {
            val persistedAthleticPreparation = repository.add(athleticTraining)
            val snapshot = athleticTraining.snapshot()
            val persistedSnapshot = persistedAthleticPreparation.snapshot()
            Assertions.assertEquals(snapshot, persistedSnapshot)
        }
        "be able to find an AthleticPreparation by id" - {
            assert(repository.findById(AthleticTrainingId("0")) == null)
            val persistedAthleticPreparation = repository.add(athleticTraining)
            val persistedId = persistedAthleticPreparation.snapshot().id
            assert(repository.findById(persistedId) != null)
        }
        "be able to find an AthleticPreparation by athleticTrainerId" - {
            repository.add(athleticTraining)
            assert(repository.findAllByAthleticTrainerId(athleticTrainerId).isNotEmpty())
        }
        "be able to find an AthleticPreparation by memberId" - {
            repository.add(athleticTraining)
            assert(repository.findAllByMemberId(memberId).isNotEmpty())
        }
        "be able to find an AthleticPreparation by periodOfPreparation" - {
            repository.add(athleticTraining)
            assert(repository.findAllByPeriodOfPreparation(periodOfPreparation).isNotEmpty())
        }
    }
})
