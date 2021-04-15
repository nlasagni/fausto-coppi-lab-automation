package it.unibo.lss.fcla.athletictraining.adapter.repository

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository
import org.junit.jupiter.api.Assertions
import java.time.LocalDate

/**
 * Test of the [InMemoryActiveAthleticTrainingRepository] class.
 *
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryActiveAthleticTrainingRepositoryTest : FreeSpec({

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
        purpose = Purpose("AthleticPreparation")
        activeAthleticTraining = ActiveAthleticTraining(
            activeAthleticTrainingId,
            athleticTrainerId,
            memberId,
            purpose,
            period
        )
    }

    "An InMemoryActiveAthleticTrainingRepository should" - {
        "be able to persist active athletic trainings" - {
            val persistedTraining = repository.add(activeAthleticTraining.snapshot())
            val snapshot = activeAthleticTraining.snapshot()
            Assertions.assertEquals(snapshot, persistedTraining)
        }
        "be able to update an already present active athletic training" - {
            val persistedTraining = repository.add(activeAthleticTraining.snapshot())
            val rehydratedAthleticTraining = ActiveAthleticTraining.rehydrate(persistedTraining)
            val newPeriodEnd = period.endDay().plusDays(5)
            rehydratedAthleticTraining.postponeTrainingPeriodEnd(newPeriodEnd)
            val updatedTraining = repository.update(rehydratedAthleticTraining.snapshot())
            updatedTraining.period.endDay().shouldBe(newPeriodEnd)
        }
        "be able to remove active athletic trainings from memory" - {
            val persistedTraining = repository.add(activeAthleticTraining.snapshot())
            repository.remove(persistedTraining)
            repository.findAllByMemberId(memberId).shouldBeEmpty()
        }
        "be able to find an active athletic training by id" - {
            assert(repository.findById(ActiveAthleticTrainingId("0")) == null)
            val persistedAthleticPreparation = repository.add(activeAthleticTraining.snapshot())
            val persistedId = persistedAthleticPreparation.id
            assert(repository.findById(persistedId) != null)
        }
        "be able to find an active athletic training by memberId" - {
            repository.add(activeAthleticTraining.snapshot())
            assert(repository.findAllByMemberId(memberId).isNotEmpty())
        }
    }
})
