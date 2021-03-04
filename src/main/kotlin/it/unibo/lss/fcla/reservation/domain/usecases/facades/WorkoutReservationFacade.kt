package it.unibo.lss.fcla.reservation.domain.usecases.facades

import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import java.util.Date
import java.util.UUID

data class WorkoutReservationFacade(
    override val date: Date,
    override val aim: String,
    override val id: UUID,
    val isOpen: Boolean
) : WorkoutReservation
