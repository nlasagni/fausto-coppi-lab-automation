package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import java.util.Calendar
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class OpenConsultingReservationTest : FreeSpec({
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    val invalidDay = 23
    val freelancerId = "0111"
    calendar.set(year, feb, day)
    val validDateOfConsulting = calendar.time
    var reservation: OpenConsultingReservation

    "An Open consulting reservation should" - {
        "have a freelancer that make a consulence and a valid date" - {
            Assertions.assertDoesNotThrow {
                reservation = OpenConsultingReservation(validDateOfConsulting, freelancerId)
                println(OpenConsultingReservation(validDateOfConsulting, freelancerId))
            }

            assertThrows<ConsultingReservationFreelancerCannotBeEmpty> {
                OpenConsultingReservation(
                    validDateOfConsulting,
                    ""
                )
            }
        }

        "A Member should" - {
            "be able to update correctly the date of a reservation" - {
                calendar.set(year, feb, 26)
                val newDateOfReservation = calendar.time
                reservation = OpenConsultingReservation(validDateOfConsulting, freelancerId)
                reservation.updateDateOfConsulting(newDateOfReservation)
            }
            "not to be able to update a reservation with an invalid date" - {
                calendar.set(year, feb, invalidDay)
                val invalidDateOfConsulting = calendar.time
                reservation = OpenConsultingReservation(validDateOfConsulting, freelancerId)
                assertThrows<OpenReservationMustNotHavePastDate> {
                    reservation.updateDateOfConsulting(invalidDateOfConsulting)
                }
            }
            "be able to update the freelancer of a reservation" - {
                reservation = OpenConsultingReservation(validDateOfConsulting, freelancerId)
                reservation.updateFreelancerOfConsulting("2222")
            }
            "not to be able to update a reservation with an invalid freelancer" - {
                calendar.set(year, feb, invalidDay)
                reservation = OpenConsultingReservation(validDateOfConsulting, freelancerId)
                assertThrows<ConsultingReservationFreelancerCannotBeEmpty> {
                    reservation.updateFreelancerOfConsulting("")
                }
            }
        }
    }
})
