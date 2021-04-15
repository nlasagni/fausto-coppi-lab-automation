/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat12

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseError
import it.unibo.lss.fcla.athletictraining.usecase.shared.model.UseCaseResponse
import java.time.LocalDate

/**
 * Test of the [Fclat12CheckActiveAthleticTrainings] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat12CheckActiveAthleticTrainingsTest : FreeSpec({

    val activeAthleticTrainingRepository = InMemoryActiveAthleticTrainingRepository()

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

    "The use case FCLAT-12 should" - {
        "allow to check all the athletic trainings of a member" - {
            activeAthleticTrainingRepository.add(activeAthleticTraining.snapshot())
            val useCase = Fclat12CheckActiveAthleticTrainings(
                object : CheckActiveAthleticTrainingsOutput {
                    override fun handleResponse(response: UseCaseResponse<Collection<ActiveAthleticTraining>>) {
                        response.response.shouldHaveSize(1)
                    }

                    override fun handleError(error: UseCaseError) {
                        // Empty
                    }
                },
                activeAthleticTrainingRepository
            )
            val request = CheckActiveAthleticTrainingsRequest(member.value)
            useCase.execute(request)
        }
    }
})
