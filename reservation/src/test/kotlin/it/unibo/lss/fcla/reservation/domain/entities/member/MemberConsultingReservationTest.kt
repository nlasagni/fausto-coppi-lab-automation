package it.unibo.lss.fcla.reservation.domain.entities.member

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import java.util.Calendar
import java.util.UUID

class MemberConsultingReservationTest : FreeSpec({
    var consultingReservationList = MemberConsultingReservation()
    val calendar = Calendar.getInstance()
    val year = 2023
    val feb = Calendar.FEBRUARY
    val day = 25
    val freelancerId = UUID.randomUUID()
    calendar.set(year, feb, day)
    val consulting = OpenConsultingReservation(calendar.time, freelancerId, UUID.randomUUID())
    "It should" - {
        "be possible to make a reservation. It will be added to an internal list" - {
            val newConsultingReservationList = consultingReservationList.addConsultingReservation(consulting)
            newConsultingReservationList.consultingReservationList.shouldNotBeEmpty()
            newConsultingReservationList.consultingReservationList.shouldContain(consulting)
        }
        "be possible to delete a reservation" - {
            val newConsultingReservationList = consultingReservationList.addConsultingReservation(consulting)
            val deletionConsultingReservationList =
                newConsultingReservationList.deleteConsultingReservation(consulting)
            deletionConsultingReservationList.consultingReservationList.shouldBeEmpty()
        }
    }
})
