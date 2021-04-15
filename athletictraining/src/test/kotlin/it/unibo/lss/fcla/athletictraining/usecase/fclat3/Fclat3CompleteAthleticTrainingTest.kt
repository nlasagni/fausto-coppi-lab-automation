/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.IncrementalGenerator
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryCompletedAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.infrastructure.service.MemberProfileUpdaterImpl
import it.unibo.lss.fcla.athletictraining.usecase.DummyCompleteAthleticTrainingPresenter
import java.time.LocalDate

/**
 * Test of the [Fclat3CompleteAthleticTraining] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat3CompleteAthleticTrainingTest : FreeSpec({

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

    val idGenerator = IncrementalGenerator()
    val activeAthleticTrainingRepository = InMemoryActiveAthleticTrainingRepository()
    val completedAthleticTrainingRepository = InMemoryCompletedAthleticTrainingRepository()
    val memberProfileUpdater = MemberProfileUpdaterImpl()
    val useCase = Fclat3CompleteAthleticTraining(
        DummyCompleteAthleticTrainingPresenter(),
        idGenerator,
        memberProfileUpdater,
        activeAthleticTrainingRepository,
        completedAthleticTrainingRepository
    )

    "The use case FCLAT-3 should" - {
        "allow the completion of an athletic training" - {
            activeAthleticTrainingRepository.add(activeAthleticTraining.snapshot())
            val request = CompleteAthleticTrainingRequest(activeAthleticTrainingId.value)
            useCase.execute(request)
            completedAthleticTrainingRepository.findAll().shouldHaveSize(1)
        }
    }
})
