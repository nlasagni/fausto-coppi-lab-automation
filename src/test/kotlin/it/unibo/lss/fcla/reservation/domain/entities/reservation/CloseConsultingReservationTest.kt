package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import org.junit.jupiter.api.assertThrows
import java.util.Calendar
import java.util.UUID

class CloseConsultingReservationTest : FreeSpec({
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    calendar.set(year, feb, day)
    val validDateOfConsulting = calendar.time
    val freelancerId = "0111"
    val closeConsultingId = UUID.randomUUID()

    "A CloseConsultingReservation should" - {
        "not to be empty" - {
            val reservation = CloseConsultingReservation(validDateOfConsulting, freelancerId, closeConsultingId)
            assert(reservation.id.toString().isNotEmpty())
            assert(reservation.id == closeConsultingId)
        }
        "be named as requested" - {
            val reservation = CloseConsultingReservation(validDateOfConsulting, freelancerId, closeConsultingId)
            assert(
                reservation.toString() ==
                    "Reservation consulting {$closeConsultingId} with freelancerId: " +
                    "$freelancerId in date $validDateOfConsulting"
            )
        }

        "have a freelancer that made a consulting" - {
            assertThrows<ConsultingReservationFreelancerCannotBeEmpty> {
                CloseConsultingReservation(validDateOfConsulting, "", closeConsultingId)
            }
        }
    }
})
