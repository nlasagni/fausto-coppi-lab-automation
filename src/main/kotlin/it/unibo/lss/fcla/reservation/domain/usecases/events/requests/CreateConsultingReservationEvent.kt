package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

/**
 * [Event] representing the creation of a consulting reservation.
 *
 * It needs the [id] of this event, the [freelancer] which is involved into the consulting reservation,
 * the [date] of the consulting reservation, the [firstName], the [lastName] and the [memberId] of the Member who
 * require the consulting.
 */
data class CreateConsultingReservationEvent(
    override val id: UUID,
    val freelancer: String,
    val date: Date,
    val firstName: String,
    val lastName: String,
    val memberId: UUID
) : Event
