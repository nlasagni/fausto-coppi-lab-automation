/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.shared

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.BeginningOfPeriodCannotBeAfterEnd
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * Tests of the [Period] Value Object.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class PeriodTest : FreeSpec({

    val validBeginning = LocalDate.now()
    val validEnd = validBeginning.plusMonths(1)
    val period = Period(validBeginning, validEnd)

    "A period should" - {
        "prevent that beginning is after end" - {
            assertThrows<BeginningOfPeriodCannotBeAfterEnd> {
                Period(validEnd, validBeginning)
            }
            assertDoesNotThrow {
                Period(validBeginning, validEnd)
            }
        }
        "be equal to another with same beginning and end" - {
            Assertions.assertEquals(period, period)
        }
        "be able to check if it overlaps with another one" - {
            period.overlapsWith(period).shouldBeTrue()
        }
        "be able to check if it ends after another one" - {
            period.endsAfter(period).shouldBeFalse()
            val periodTwo = period.changeEndDay(validEnd.plusDays(1))
            periodTwo.endsAfter(period).shouldBeTrue()
        }
    }
})
