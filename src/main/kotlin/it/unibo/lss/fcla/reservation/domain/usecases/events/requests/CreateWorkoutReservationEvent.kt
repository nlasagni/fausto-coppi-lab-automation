package it.unibo.lss.fcla.reservation.domain.usecases.events.requests

import it.unibo.lss.fcla.reservation.common.Event
import java.util.Date
import java.util.UUID

data class CreateWorkoutReservationEvent(
    override val id: UUID,
    val aim: String,
    val date: Date) : Event
