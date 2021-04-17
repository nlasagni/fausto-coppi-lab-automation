/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.adapter.idgenerator.UuidGenerator
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.AthleticTrainingMustHaveMember
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * Tests of the [CompletedAthleticTraining] Aggregate Root.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class CompletedAthleticTrainingTest : FreeSpec({

    lateinit var id: CompletedAthleticTrainingId
    lateinit var athleticTrainer: AthleticTrainerId
    lateinit var member: MemberId
    lateinit var completedWorkouts: Collection<CompletedWorkout>
    lateinit var beginning: LocalDate
    lateinit var end: LocalDate
    lateinit var period: Period
    lateinit var purpose: Purpose

    /**
     * Setup before every test.
     */
    beforeAny {
        id = CompletedAthleticTrainingId(UuidGenerator().generate())
        athleticTrainer = AthleticTrainerId("1234")
        member = MemberId("1234")
        completedWorkouts = emptyList()
        beginning = LocalDate.now()
        end = beginning.plusMonths(1)
        period = Period(beginning, end)
        purpose = Purpose("Athletic Preparation")
    }

    "A completed athletic training should" - {
        "be related to a member, an athletic trainer, and a valid period" - {
            Assertions.assertDoesNotThrow {
                CompletedAthleticTraining(
                    id,
                    athleticTrainer,
                    member,
                    purpose,
                    period,
                    completedWorkouts
                )
            }
            assertThrows<AthleticTrainingMustHaveAthleticTrainer> {
                CompletedAthleticTraining(
                    id,
                    AthleticTrainerId(""),
                    member,
                    purpose,
                    period,
                    completedWorkouts
                )
            }
            assertThrows<AthleticTrainingMustHaveMember> {
                CompletedAthleticTraining(
                    id,
                    athleticTrainer,
                    MemberId(""),
                    purpose,
                    period,
                    completedWorkouts
                )
            }
        }
    }
})
