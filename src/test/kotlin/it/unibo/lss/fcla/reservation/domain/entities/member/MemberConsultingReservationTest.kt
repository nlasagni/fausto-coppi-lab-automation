package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import java.util.Calendar
import java.util.UUID

class MemberConsultingReservationTest : FreeSpec({
    var consultingReservationList = MemberConsultingReservation()
    val calendar = Calendar.getInstance()
    val year = 2021
    val feb = 2
    val day = 25
    val freelancerId = "0111"
    calendar.set(year, feb, day)
    val consulting = OpenConsultingReservation(calendar.time, freelancerId, UUID.randomUUID())
    "It should" - {
        "be possible to make a reservation. It will be added to an internal list" - {
            val newConsultingReservationList = consultingReservationList.addConsultingReservation(consulting)
            assert(newConsultingReservationList.retrieveAllMemberConsulting().isNotEmpty())
            assert(newConsultingReservationList.retrieveAllMemberConsulting().contains(consulting))
        }
        "be possible to delete a reservation" - {
            val newConsultingReservationList = consultingReservationList.addConsultingReservation(consulting)
            val deletionConsultingReservationList =
                newConsultingReservationList.deleteConsultingReservation(consulting)
            assert(deletionConsultingReservationList.retrieveAllMemberConsulting().isEmpty())
        }
    }
})
