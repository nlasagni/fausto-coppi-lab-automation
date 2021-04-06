package it.unibo.lss.fcla.athletictraining.adapter.repository

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.usecase.port.ActiveAthleticTrainingRepository
import org.junit.jupiter.api.Assertions
import java.time.LocalDate

/**
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryAthleticPreparationRepositoryTest : FreeSpec({

    lateinit var repository: ActiveAthleticTrainingRepository
    lateinit var activeAthleticTrainingId: ActiveAthleticTrainingId
    lateinit var athleticTrainerId: AthleticTrainerId
    lateinit var memberId: MemberId
    lateinit var period: Period
    lateinit var purpose: Purpose
    lateinit var activeAthleticTraining: ActiveAthleticTraining

    beforeAny {
        repository = InMemoryActiveAthleticTrainingRepository()
        val now = LocalDate.now()
        activeAthleticTrainingId = ActiveAthleticTrainingId(UuidGenerator().generate())
        athleticTrainerId = AthleticTrainerId("1234")
        memberId = MemberId("1234")
        period = Period(
            now,
            now.plusMonths(1)
        )
        purpose = Purpose.AthleticPreparation()
        activeAthleticTraining = ActiveAthleticTraining(
            activeAthleticTrainingId,
            athleticTrainerId,
            memberId,
            purpose,
            period
        )
    }

    "An InMemoryAthleticPreparationRepository should" - {
        "be able to persist AthleticPreparation" - {
            val persistedAthleticPreparation = repository.add(activeAthleticTraining)
            val snapshot = activeAthleticTraining.snapshot()
            val persistedSnapshot = persistedAthleticPreparation.snapshot()
            Assertions.assertEquals(snapshot, persistedSnapshot)
        }
        "be able to find an AthleticPreparation by id" - {
            assert(repository.findById(ActiveAthleticTrainingId("0")) == null)
            val persistedAthleticPreparation = repository.add(activeAthleticTraining)
            val persistedId = persistedAthleticPreparation.snapshot().id
            assert(repository.findById(persistedId) != null)
        }
        "be able to find an AthleticPreparation by athleticTrainerId" - {
            repository.add(activeAthleticTraining)
            assert(repository.findAllByAthleticTrainerId(athleticTrainerId).isNotEmpty())
        }
        "be able to find an AthleticPreparation by memberId" - {
            repository.add(activeAthleticTraining)
            assert(repository.findAllByMemberId(memberId).isNotEmpty())
        }
        "be able to find an AthleticPreparation by periodOfPreparation" - {
            repository.add(activeAthleticTraining)
            assert(repository.findAllByPeriodOfPreparation(period).isNotEmpty())
        }
    }
})
