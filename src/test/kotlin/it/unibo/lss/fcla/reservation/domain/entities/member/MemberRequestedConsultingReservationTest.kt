package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import java.util.*

class MemberRequestedConsultingReservationTest: FreeSpec({
    var consultingReservationList = MemberRequestedConsultingReservation()
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    val freelancerId = "0111"
    calendar.set(year, feb, day)
    "It should" - {
        "be possible to make a reservation. It will be added to an internal list" - {
            val consulting = OpenConsultingReservation(calendar.time, freelancerId)
            consultingReservationList.addConsultingReservation(consulting)
            assert(consultingReservationList.getAllMemberConsulting().isNotEmpty())
            assert(consultingReservationList.getAllMemberConsulting().contains(consulting))
        }
        "be possible to delete a reservation" - {

        }
    }
})