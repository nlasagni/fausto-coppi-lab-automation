package it.unibo.lss.fcla.reservation

import io.kotest.core.spec.style.FreeSpec
import java.util.Calendar
import java.util.Date

class DeskTest : FreeSpec({
    val cal = Calendar.getInstance()
    cal.set(2021, 2, 13)
    val date: Date = cal.time

    val member = Member(
        "Mario",
        "Rossi",
        MembershipCard("0111")
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

            desk.createGymRes(date, member, machine)
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
            desk.createConsRes(date, member, professional, nameCons)

            val regRes = desk.read(
                member,
                null
            )
            assert(regRes.contains(res))
        }
        "Update reservation date" - {
            cal.set(2021, 3, 13)
            val oldDate: Date = cal.time
            cal.set(2021, 4, 23)
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

            desk.createGymRes(
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
            cal.set(2021, 5, 3)
            val delDate: Date = cal.time

            val res = ReservationGym(
                delDate,
                member,
                machine
            )

            desk.createGymRes(
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
