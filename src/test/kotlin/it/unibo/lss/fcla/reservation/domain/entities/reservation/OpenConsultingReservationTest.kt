package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.ConsultingReservationFreelancerCannotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.exceptions.OpenReservationMustNotHavePastDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import java.util.Calendar
import java.util.UUID

class OpenConsultingReservationTest : FreeSpec({
    val calendar = Calendar.getInstance()
    val year = 2022
    val invalidYear = 2021
    val feb = 2
    val day = 25
    val freelancerId = "0111"
    calendar.set(year, feb, day)
    val validDateOfConsulting = calendar.time
    val openConsultingReservationId = UUID.randomUUID()

    var reservation = OpenConsultingReservation(
        validDateOfConsulting,
        freelancerId,
        openConsultingReservationId
    )


    "An Open consulting reservation should" - {
        "have a freelancer that make a consulting and a valid date" - {
            Assertions.assertDoesNotThrow {
                reservation = OpenConsultingReservation(
                    validDateOfConsulting,
                    freelancerId,
                    openConsultingReservationId
                )
            }

            assertThrows<ConsultingReservationFreelancerCannotBeEmpty> {
                OpenConsultingReservation(
                    validDateOfConsulting,
                    "",
                    openConsultingReservationId
                )
            }
        }
        "not to be empty" - {
            assert(reservation.value().toString().isNotEmpty())
        }
        "have correct UUID" - {
            assert(reservation.value().toString().isNotEmpty())
            assert(reservation.value() == openConsultingReservationId)
        }
        "be named as requested" - {
            assert(reservation.toString() ==
                    "Reservation consulting {$openConsultingReservationId} with freelancerId: " +
                    "$freelancerId in date $validDateOfConsulting")
        }

        "A Member should" - {
            "be able to update correctly the date of a reservation" - {
                calendar.set(year, feb, 26)
                val newDateOfReservation = calendar.time
                val newReservation = reservation.updateDateOfConsulting(newDateOfReservation)
                println("test di correttezza valori")
                println("NewDate $newDateOfReservation")
                assert(newReservation.freelancerId == freelancerId)
                assert(newReservation.date == newDateOfReservation)
                assert(newReservation.id == openConsultingReservationId)
            }
            "not to be able to update a reservation with an invalid date" - {
                calendar.set(invalidYear, feb, day)
                val invalidDateOfConsulting = calendar.time
                assertThrows<OpenReservationMustNotHavePastDate> {
                    reservation.updateDateOfConsulting(invalidDateOfConsulting)
                }
            }
            "be able to update the freelancer of a reservation" - {
                val newFreelancerId = "2222"
                val updatedFreelancer = reservation.updateFreelancerOfConsulting(newFreelancerId)
                assert(updatedFreelancer.freelancerId == newFreelancerId)
            }
            "not to be able to update a reservation with an invalid freelancer" - {
                assertThrows<ConsultingReservationFreelancerCannotBeEmpty> {
                    reservation.updateFreelancerOfConsulting("")
                }
            }
        }
    }
})
