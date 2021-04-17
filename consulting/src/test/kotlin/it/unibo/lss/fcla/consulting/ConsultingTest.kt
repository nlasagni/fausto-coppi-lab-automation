/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingType
import it.unibo.lss.fcla.consulting.domain.consulting.createNutritionistConsulting
import it.unibo.lss.fcla.consulting.domain.consulting.createPhysiotherapyConsulting
import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingMustHaveAValidId
import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingMustHaveAValidMember
import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingSummaryDescriptionCannotBeEmpty
import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingSummaryMustHaveAValidFreelancer
import java.time.LocalDate

class ConsultingTest : FreeSpec({
    "A consulting should be created with a valid Id" - {
        shouldThrow<ConsultingMustHaveAValidId> {
            Consulting.createNutritionistConsulting(
                consultingId = "",
                memberId = "123",
                consultingDate = LocalDate.of(2021, 1, 1),
                freelancerId = "1234",
                description = "description"
            )
        }
    }

    "A consulting should be created with a valid member" - {
        shouldThrow<ConsultingMustHaveAValidMember> {
            Consulting.createPhysiotherapyConsulting(
                consultingId = "1234",
                memberId = "",
                consultingDate = LocalDate.of(2021, 1, 1),
                freelancerId = "1234",
                description = "description"
            )
        }
    }

    "A consulting summary should be created with a valid freelancer" - {
        shouldThrow<ConsultingSummaryMustHaveAValidFreelancer> {
            Consulting.createPhysiotherapyConsulting(
                consultingId = "1234",
                memberId = "123",
                consultingDate = LocalDate.of(2021, 1, 1),
                freelancerId = "",
                description = "description"
            )
        }
    }

    "A consulting summary should be created with no empty description" - {
        shouldThrow<ConsultingSummaryDescriptionCannotBeEmpty> {
            Consulting.createPhysiotherapyConsulting(
                consultingId = "1234",
                memberId = "123",
                consultingDate = LocalDate.of(2021, 1, 1),
                freelancerId = "F1234",
                description = ""
            )
        }
    }

    "Consulting type equality" - {
        val firstNutritionConsulting = ConsultingType.NutritionConsulting()
        val secondNutritionConsulting = ConsultingType.NutritionConsulting()

        assert(firstNutritionConsulting.equals(secondNutritionConsulting))
    }

    "Consulting type inequality" - {
        val nutritionConsulting = ConsultingType.NutritionConsulting()
        val athleticTrainerConsulting = ConsultingType.AthleticTrainerConsulting()

        assert(!nutritionConsulting.equals(athleticTrainerConsulting))
    }
})
