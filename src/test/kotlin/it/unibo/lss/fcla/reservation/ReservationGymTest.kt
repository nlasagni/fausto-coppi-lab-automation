package it.unibo.lss.fcla.reservation

import io.kotest.core.spec.style.FreeSpec
import java.util.Calendar
import java.util.Date

class ReservationGymTest : FreeSpec({

    "ReservationGym should" - {
        "update the date of a reservation" - {
            val cal = Calendar.getInstance()
            cal.set(2021, 2, 13)
            val oldDate: Date = cal.time
            cal.set(2021, 2, 23)
            val newDate: Date = cal.time

            val oldResGym = ReservationGym(
                oldDate,
                Member(
                    "Mario",
                    "Rossi",
                    MembershipCard("000")
                ),
                Machine("SkillBike")
            )

            val updatedRes = oldResGym.updateDate(newDate)
            assert(updatedRes.member == oldResGym.member)
            assert(updatedRes.date == newDate)
        }
    }
})
