package it.unibo.lss.fcla.reservation.domain.usecases.facades

import java.util.Date
import java.util.UUID

/**
 * Facade used to have access to a consulting reservation and associate the [reservationId] to its [date].
 */
class WorkoutReservationDateFacade(val reservationId: UUID, val date: Date)
