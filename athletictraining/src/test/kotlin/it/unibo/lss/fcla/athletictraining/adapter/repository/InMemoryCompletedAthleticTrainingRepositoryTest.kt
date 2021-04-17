/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.adapter.repository

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.CompletedAthleticTrainingRepository
import org.junit.jupiter.api.Assertions
import java.time.LocalDate

/**
 * Test of the [InMemoryCompletedAthleticTrainingRepository] class.
 *
 * @author Nicola Lasagni on 12/04/2021.
 */
class InMemoryCompletedAthleticTrainingRepositoryTest : FreeSpec({

    lateinit var repository: CompletedAthleticTrainingRepository
    lateinit var id: CompletedAthleticTrainingId
    lateinit var athleticTrainerId: AthleticTrainerId
    lateinit var memberId: MemberId
    lateinit var period: Period
    lateinit var purpose: Purpose
    lateinit var completedAthleticTraining: CompletedAthleticTraining

    beforeAny {
        repository = InMemoryCompletedAthleticTrainingRepository()
        val now = LocalDate.now()
        id = CompletedAthleticTrainingId(UuidGenerator().generate())
        athleticTrainerId = AthleticTrainerId("1234")
        memberId = MemberId("1234")
        period = Period(
            now,
            now.plusMonths(1)
        )
        purpose = Purpose("AthleticPreparation")
        completedAthleticTraining = CompletedAthleticTraining(
            id,
            athleticTrainerId,
            memberId,
            purpose,
            period,
            emptyList()
        )
    }

    "An InMemoryCompletedAthleticTrainingRepository should" - {
        "be able to persist completed athletic trainings" - {
            val persistedAthleticTraining = repository.add(completedAthleticTraining)
            Assertions.assertEquals(completedAthleticTraining, persistedAthleticTraining)
        }
        "be able to find all the completed athletic trainings" - {
            repository.add(completedAthleticTraining)
            repository.findAll().shouldHaveSize(1)
        }
    }
})
