package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeUUID
import io.kotest.matchers.string.shouldNotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import java.util.Calendar
import java.util.UUID

class CloseConsultingReservationTest : FreeSpec({
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    calendar.set(year, feb, day)
    val validDateOfConsulting = calendar.time
    val freelancerId = UUID.randomUUID()
    val invalidFreelancer = UUID(0, 0)
    val closeConsultingId = UUID.randomUUID()

    "A CloseConsultingReservation should" - {
        "not to be empty" - {
            val reservation = CloseConsultingReservation(validDateOfConsulting, freelancerId, closeConsultingId)
            reservation.id.toString().shouldBeUUID()
            reservation.id.toString().shouldNotBeEmpty()
            reservation.id.shouldBe(closeConsultingId)
        }
        "be named as requested" - {
            val reservation = CloseConsultingReservation(validDateOfConsulting, freelancerId, closeConsultingId)
            reservation.toString().shouldBe(
                "Reservation consulting {$closeConsultingId} with freelancerId: " +
                    "$freelancerId in date $validDateOfConsulting"
            )
            reservation.freelancerId.value.toString().shouldBeUUID()
            reservation.freelancerId.value.shouldBe(freelancerId)
            reservation.id.hashCode().shouldBe(closeConsultingId.hashCode())
        }

        "have a freelancer that made a consulting" - {
            shouldThrow<ConsultingReservationFreelancerCannotBeEmpty> {
                CloseConsultingReservation(
                    validDateOfConsulting,
                    invalidFreelancer,
                    closeConsultingId
                )
            }
        }
    }
})
