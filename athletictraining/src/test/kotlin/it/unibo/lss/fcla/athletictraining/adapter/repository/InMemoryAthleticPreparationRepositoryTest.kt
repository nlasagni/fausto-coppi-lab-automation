package it.unibo.lss.fcla.athletictraining.adapter.repository

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.Period
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.Purpose
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
    lateinit var period: Period
    lateinit var purpose: Purpose
    lateinit var athleticTraining: AthleticTraining

    beforeAny {
        repository = InMemoryAthleticPreparationRepository()
        val now = LocalDate.now()
        athleticTrainerId = AthleticTrainerId("1234")
        memberId = MemberId("1234")
        period = Period(
            now,
            now.plusMonths(Period.minimumPeriodDurationInMonth.toLong())
        )
        purpose = Purpose.AthleticPreparation()
        athleticTraining = AthleticTraining(
            athleticTrainerId,
            memberId,
            purpose,
            period
        )
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
            assert(repository.findAllByPeriodOfPreparation(period).isNotEmpty())
        }
    }
})
