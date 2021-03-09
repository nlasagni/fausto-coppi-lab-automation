package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

/**
 * An event representing the creation of a workout reservation.
 *
 * It needs the [id] of this event, the [aim] of the workout reservation,
 * the [date] of the consulting reservation, the [firstName], the [lastName] and the [memberId] of the Member who
 * require the consulting.
 */
data class CreateWorkoutReservationEvent(
    override val id: UUID,
    val aim: String,
    val date: Date,
    val firstName: String,
    val lastName: String,
    val memberId: UUID
) : Event
