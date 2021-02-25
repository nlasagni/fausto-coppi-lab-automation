package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.CloseReservationCannotBeUpdated
import org.junit.jupiter.api.assertThrows
import java.util.Calendar

class CloseConsultingReservationTest : FreeSpec({
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    val invalidDay = 23
    val freelancerId = "0111"
    val freelancerIdUpdate = "0110"
    calendar.set(year, feb, day)
    val validDateOfConsulting = calendar.time
    calendar.set(year, feb, invalidDay)
    val invalidDateOfConsulting = calendar.time

    "A Member should not" - {
        "to be able to modify a closed reservation" - {
            val reservation = CloseConsultingReservation(validDateOfConsulting, freelancerId)
            assertThrows<CloseReservationCannotBeUpdated> {
                reservation.updateDateOfConsulting(invalidDateOfConsulting)
            }
            assertThrows<CloseReservationCannotBeUpdated> {
                reservation.updateFreelancerOfConsulting(freelancerIdUpdate)
            }
        }
    }
})
