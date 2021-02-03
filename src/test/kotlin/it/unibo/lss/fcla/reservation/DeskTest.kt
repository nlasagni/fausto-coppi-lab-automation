package it.unibo.lss.fcla.reservation

import io.kotest.core.spec.style.FreeSpec
import java.util.Calendar
import java.util.Date

class DeskTest : FreeSpec({

    "Desk should" - {
        "Create new gym reservation" - {
            val cal = Calendar.getInstance()
            cal.set(2021, 2, 13)
            val date: Date = cal.time
            val member = Member(
                "Mario",
                "Rossi",
                MembershipCard("0111")
            )
            val machine = Machine("Rullo")

            val res = ReservationGym(
                date,
                member,
                machine
            )

            val desk = Desk()
            desk.createGymRes(date, member, machine)
            val regRes = desk.read(
                member,
                null
            ).first() as ReservationGym
            assert(regRes.member == res.member)
            assert(regRes.date == res.date)
            assert(regRes.machine == res.machine)
        }
        "Create new consulting reservation" - {
            val cal = Calendar.getInstance()
            cal.set(2021, 2, 13)
            val date: Date = cal.time
            val member = Member(
                "Mario",
                "Rossi",
                MembershipCard("0111")
            )
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

            val desk = Desk()
            desk.createConsRes(date, member, professional, nameCons)
            val regRes = desk.read(
                member,
                null
            ).first() as ReservationConsulting
            println(professional)
            println(res)
            assert(regRes.member == res.member)
            assert(regRes.date == res.date)
            assert(regRes.professional == res.professional)
            assert(regRes.name == res.name)
        }
        "Update reservation date" - {
            val cal = Calendar.getInstance()
            cal.set(2021, 2, 13)
            val oldDate: Date = cal.time
            cal.set(2021, 2, 23)
            val newDate: Date = cal.time

            val member = Member(
                "Mario",
                "Rossi",
                MembershipCard("0111")
            )
            val machine = Machine("Rullo")

            val res = ReservationGym(
                oldDate,
                member,
                machine
            )

            val desk = Desk()
            desk.createGymRes(
                oldDate,
                member,
                machine
            )

            desk.update(res, newDate)

            val regRes = desk.read(
                member,
                null
            ).first() as ReservationGym
            assert(regRes.member == res.member)
            assert(regRes.date == newDate)
            assert(regRes.machine == res.machine)
        }
        "Delete reservation date" - {
            val cal = Calendar.getInstance()
            cal.set(2021, 2, 13)
            val date: Date = cal.time

            val member = Member(
                "Mario",
                "Rossi",
                MembershipCard("0111")
            )
            val machine = Machine("Rullo")

            val res = ReservationGym(
                date,
                member,
                machine
            )

            val desk = Desk()
            desk.createGymRes(
                date,
                member,
                machine
            )

            desk.delete(res)

            val regRes = desk.read(
                null,
                null
            )

            assert(regRes.isEmpty())
        }
    }
})
