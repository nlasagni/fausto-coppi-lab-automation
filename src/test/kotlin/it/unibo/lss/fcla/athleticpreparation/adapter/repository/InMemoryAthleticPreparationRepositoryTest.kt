package it.unibo.lss.fcla.athleticpreparation.adapter.repository

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticPreparation
import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticPreparationId
import it.unibo.lss.fcla.athleticpreparation.domain.model.PeriodOfPreparation
import it.unibo.lss.fcla.athleticpreparation.usecase.port.AthleticPreparationRepository
import org.junit.jupiter.api.Assertions
import java.time.LocalDate

/**
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryAthleticPreparationRepositoryTest : FreeSpec({

    lateinit var repository: AthleticPreparationRepository
    lateinit var athleticTrainerId: String
    lateinit var memberId: String
    lateinit var periodOfPreparation: PeriodOfPreparation
    lateinit var athleticPreparation: AthleticPreparation

    beforeAny {
        repository = InMemoryAthleticPreparationRepository()
        val now = LocalDate.now()
        athleticTrainerId = "1234"
        memberId = "1234"
        periodOfPreparation = PeriodOfPreparation(
                now,
                now.plusMonths(PeriodOfPreparation.minimumPeriodDurationInMonth.toLong())
        )
        athleticPreparation = AthleticPreparation(athleticTrainerId, memberId, periodOfPreparation)
    }

    "An InMemoryAthleticPreparationRepository should" - {
        "be able to persist AthleticPreparation" - {
            val persistedAthleticPreparation = repository.add(athleticPreparation)
            val snapshot = athleticPreparation.snapshot()
            val persistedSnapshot = persistedAthleticPreparation.snapshot()
            Assertions.assertEquals(snapshot, persistedSnapshot)
        }
        "be able to find an AthleticPreparation by id" - {
            assert(repository.findById(AthleticPreparationId("0")) == null)
            val persistedAthleticPreparation = repository.add(athleticPreparation)
            val persistedId = persistedAthleticPreparation.snapshot().id
            assert(repository.findById(persistedId) != null)
        }
        "be able to find an AthleticPreparation by athleticTrainerId" - {
            repository.add(athleticPreparation)
            assert(repository.findAllByAthleticTrainerId(athleticTrainerId).isNotEmpty())
        }
        "be able to find an AthleticPreparation by memberId" - {
            repository.add(athleticPreparation)
            assert(repository.findAllByMemberId(memberId).isNotEmpty())
        }
        "be able to find an AthleticPreparation by periodOfPreparation" - {
            repository.add(athleticPreparation)
            assert(repository.findAllByPeriodOfPreparation(periodOfPreparation).isNotEmpty())
        }
    }
})