package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservation
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
        is AgendaAddConsultingReservation -> state.addConsultingReservation(event.reservation)
        is AgendaAddWorkoutReservation -> state.addWorkoutReservation(event.reservation)
        is AgendaDeleteConsultingReservation -> state.deleteConsultingReservation(event.reservation)
        is AgendaDeleteWorkoutReservation -> state.deleteWorkoutReservation(event.reservation)
        else -> state
    }
}
