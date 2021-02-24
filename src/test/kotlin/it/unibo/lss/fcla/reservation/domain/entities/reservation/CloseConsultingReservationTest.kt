package it.unibo.lss.fcla.reservation.domain.entities.reservation

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.reservation.domain.exceptions.CloseConsultingCannotBeUpdated
import org.junit.jupiter.api.assertThrows

class CloseConsultingReservationTest: FreeSpec ({
    "A Member should not" - {
        "to be able to modify a closed reservation" - {
            val reservation = CloseConsultingReservation()
            assertThrows<CloseConsultingCannotBeUpdated> {
                reservation.updateDateOfConsulting()
            }
        }
    }
})
