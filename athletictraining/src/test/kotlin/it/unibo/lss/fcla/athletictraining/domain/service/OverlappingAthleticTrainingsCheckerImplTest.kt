package it.unibo.lss.fcla.athletictraining.domain.service

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import java.time.LocalDate

/**
 * Test of the [OverlappingAthleticTrainingsChecker] service implementation.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class OverlappingAthleticTrainingsCheckerImplTest : FreeSpec({

    lateinit var activeAthleticTrainingId: ActiveAthleticTrainingId
    lateinit var athleticTrainer: AthleticTrainerId
    lateinit var member: MemberId
    lateinit var beginning: LocalDate
    lateinit var end: LocalDate
    lateinit var period: Period
    lateinit var purpose: Purpose
    lateinit var activeAthleticTraining: ActiveAthleticTraining

    beforeAny {
        activeAthleticTrainingId = ActiveAthleticTrainingId(UuidGenerator().generate())
        athleticTrainer = AthleticTrainerId("1234")
        member = MemberId("1234")
        beginning = LocalDate.now()
        end = beginning.plusMonths(1)
        period = Period(beginning, end)
        purpose = Purpose("Athletic Preparation")
        activeAthleticTraining = ActiveAthleticTraining(
            activeAthleticTrainingId,
            athleticTrainer,
            member,
            purpose,
            period
        )
    }

    "The OverlappingAthleticTrainingsChecker should be able to" - {
        "check if there is one or more athletic training with overlapping periods" - {
            val athleticTrainings = listOf(
                activeAthleticTraining,
                activeAthleticTraining
            )
            val service = OverlappingAthleticTrainingsCheckerImpl()
            service.existsOverlappingAthleticTraining(athleticTrainings, period).shouldBeTrue()
        }
    }
})
