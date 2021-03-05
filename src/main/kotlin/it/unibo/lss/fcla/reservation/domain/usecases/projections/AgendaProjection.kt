package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservationEvent
import java.util.UUID

/**
 * Projection used to update the [Agenda] given its events
 */
class AgendaProjection(override val init: Agenda) : Projection<Agenda> {

    constructor(agendaId: UUID) : this(Agenda(agendaId))

    /**
     * Return an updated [Agenda] based on the given event.
     */
    override fun update(state: Agenda, event: Event): Agenda = when (event) {
        is AgendaAddConsultingReservationEvent -> state.addConsultingReservation(event.reservation)
        is AgendaAddWorkoutReservationEvent -> state.addWorkoutReservation(event.reservation)
        is AgendaDeleteConsultingReservationEvent -> state.deleteConsultingReservation(event.reservation)
        is AgendaDeleteWorkoutReservationEvent -> state.deleteWorkoutReservation(event.reservation)
        else -> state
    }
}
