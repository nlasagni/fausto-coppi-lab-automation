package it.unibo.lss.fcla.reservation

import io.kotest.core.spec.style.FreeSpec
import java.util.Calendar
import java.util.Date

class DeskTest : FreeSpec({
    val year = 2021
    val feb = 2
    val day = 13
    val cardCode = "0111"

    val cal = Calendar.getInstance()
    cal.set(year, feb, day)
    val date: Date = cal.time

    val member = Member(
        "Mario",
        "Rossi",
        MembershipCard(cardCode)
    )

    val machine = Machine("Rullo")

    val desk = Desk()

    "Desk should" - {
        "Create new gym reservation" - {
            val res = ReservationGym(
                date,
                member,
                machine
            )

            desk.createGymReservation(date, member, machine)
            val regRes = desk.read(
                member,
                null
            )
            assert(regRes.contains(res))
        }
        "Create new consulting reservation" - {
            val professional = Professional(
                ProfessionalType.ATHLETIC_TRAINER,
                "Paolo",
                "Bianchi"
            )
            val nameCons = Consulting.ATHLETIC_TRAINING

            val res = ReservationConsulting(
                date,
                member,
                professional,
                nameCons
            )
            desk.createConsultingReservation(date, member, professional, nameCons)

            val regRes = desk.read(
                member,
                null
            )
            assert(regRes.contains(res))
        }
        "Update reservation date" - {
            val march = 3
            val april = 4
            val day2 = 23
            cal.set(year, march, day)
            val oldDate: Date = cal.time
            cal.set(year, april, day2)
            val newDate: Date = cal.time

            val res = ReservationGym(
                oldDate,
                member,
                machine
            )

            val newRes = ReservationGym(
                newDate,
                member,
                machine
            )

            desk.createGymReservation(
                oldDate,
                member,
                machine
            )

            desk.update(res, newDate)

            val regRes = desk.read(
                null,
                newDate
            )
            assert(regRes.contains(newRes))
        }
        "Delete reservation date" - {
            val may = 5
            val day3 = 3
            cal.set(year, may, day3)
            val delDate: Date = cal.time

            val res = ReservationGym(
                delDate,
                member,
                machine
            )

            desk.createGymReservation(
                delDate,
                member,
                machine
            )

            desk.delete(res)

            val regRes = desk.read(
                null,
                null
            )

            assert(!regRes.contains(res))
        }
    }
})
