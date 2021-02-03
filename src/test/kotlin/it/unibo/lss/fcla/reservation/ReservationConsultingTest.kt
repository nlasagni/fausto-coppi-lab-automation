package it.unibo.lss.fcla.reservation

import io.kotest.core.spec.style.FreeSpec
import java.util.Calendar
import java.util.Date

class ReservationConsultingTest : FreeSpec({

    "ReservationConsulting should" - {
        "update the date of a reservation" - {
            val cal = Calendar.getInstance()
            cal.set(2021, 2, 13)
            val oldDate: Date = cal.time
            cal.set(2021, 2, 23)
            val newDate: Date = cal.time

            val oldResConsulting = ReservationConsulting(
                oldDate,
                Member(
                    "Mario",
                    "Rossi",
                    MembershipCard("000")
                ),
                Professional(
                    ProfessionalType.ATHLETIC_TRAINER,
                    "Paolo",
                    "Rossi"
                ),
                Consulting.ATHLETIC_TRAINING
            )

            val updatedResConsulting = oldResConsulting.updateDate(newDate)
            assert(updatedResConsulting.member == oldResConsulting.member)
            assert(updatedResConsulting.date == newDate)
            assert(updatedResConsulting.professional == oldResConsulting.professional)
        }
    }
})
