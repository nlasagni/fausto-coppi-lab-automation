package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import it.unibo.lss.fcla.athletictraining.domain.exception.BeginningOfScheduleCannotBeAfterEnd
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Test of the [Schedule] Value Object.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class ScheduleTest : FreeSpec({

    val today = LocalDate.now()

    "A schedule should" - {
        "prevent that beginning is after end" - {
            assertThrows<BeginningOfScheduleCannotBeAfterEnd> {
                Schedule(
                    today,
                    LocalTime.now().plusHours(1),
                    LocalTime.now()
                )
            }
        }
        "allow to check if it overlaps with another schedule" - {
            val tomorrow = LocalDate.now().plusDays(1)
            val now = LocalTime.now()
            val inAnHour = now.plusHours(1)
            val halfAnHourAgo = now.minusMinutes(30)
            val oneHourAgo = now.minusHours(1)
            val firstSchedule = Schedule(today, now, inAnHour)
            val secondSchedule = Schedule(today, halfAnHourAgo, inAnHour)
            val thirdSchedule = Schedule(today, oneHourAgo, now)
            val fourthSchedule = Schedule(tomorrow, now, inAnHour)
            firstSchedule.overlapsWith(firstSchedule).shouldBeTrue()
            firstSchedule.overlapsWith(secondSchedule).shouldBeTrue()
            firstSchedule.overlapsWith(thirdSchedule).shouldBeFalse()
            firstSchedule.overlapsWith(fourthSchedule).shouldBeFalse()
        }
    }
})