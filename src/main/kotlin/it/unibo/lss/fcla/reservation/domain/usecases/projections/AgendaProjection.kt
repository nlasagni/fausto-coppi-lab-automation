package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.agenda.Agenda
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaAddWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteConsultingReservationEvent
import it.unibo.lss.fcla.reservation.domain.entities.events.agenda.AgendaDeleteWorkoutReservationEvent
import java.util.UUID

class AgendaProjection(val init: Agenda) {

    constructor(agendaId: UUID) : this(Agenda(agendaId))

    fun update(agenda: Agenda, event: Event): Agenda = when (event) {
        is AgendaAddConsultingReservationEvent -> agenda.addConsultingReservation(event.reservation)
        is AgendaAddWorkoutReservationEvent -> agenda.addWorkoutReservation(event.reservation)
        is AgendaDeleteConsultingReservationEvent -> agenda.deleteConsultingReservation(event.reservation)
        is AgendaDeleteWorkoutReservationEvent -> agenda.deleteWorkoutReservation(event.reservation)
        else -> agenda
    }
}
