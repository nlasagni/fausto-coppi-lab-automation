package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.BeginningOfScheduleCannotBeAfterEnd
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

/**
 * Test of the [Schedule] Value Object.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class ScheduleTest : FreeSpec({

    val eightOClock = LocalTime.of(8, 0)
    val today = LocalDate.now()

    "A schedule should" - {
        "prevent that beginning is after end" - {
            assertThrows<BeginningOfScheduleCannotBeAfterEnd> {
                Schedule(
                    today,
                    eightOClock.plusHours(1),
                    eightOClock
                )
            }
        }
        "allow to check if it overlaps with another schedule" - {
            val tomorrow = LocalDate.now().plusDays(1)
            val inAnHour = eightOClock.plusHours(1)
            val halfAnHourAgo = eightOClock.minusMinutes(30)
            val oneHourAgo = eightOClock.minusHours(1)
            val firstSchedule = Schedule(today, eightOClock, inAnHour)
            val secondSchedule = Schedule(today, halfAnHourAgo, inAnHour)
            val thirdSchedule = Schedule(today, oneHourAgo, eightOClock)
            val fourthSchedule = Schedule(tomorrow, eightOClock, inAnHour)
            firstSchedule.overlapsWith(firstSchedule).shouldBeTrue()
            firstSchedule.overlapsWith(secondSchedule).shouldBeTrue()
            firstSchedule.overlapsWith(thirdSchedule).shouldBeFalse()
            firstSchedule.overlapsWith(fourthSchedule).shouldBeFalse()
        }
    }
})