package it.unibo.lss.fcla.reservation.domain.entities.agenda

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.WorkoutReservation

class Agenda(
    private val agendaWorkoutReservation: AgendaWorkoutReservation,
    private val agendaConsultingReservation: AgendaConsultingReservation
) {

    constructor() : this(AgendaWorkoutReservation(),AgendaConsultingReservation())

    fun addConsultingReservation(reservation: ConsultingReservation): Agenda {
        return Agenda(agendaWorkoutReservation, agendaConsultingReservation.addConsultingReservation(reservation))
    }

    fun addWorkoutReservation(reservation: WorkoutReservation): Agenda {
        return Agenda(agendaWorkoutReservation.addWorkoutReservation(reservation), agendaConsultingReservation)
    }

    fun deleteConsultingReservation(reservation: ConsultingReservation): Agenda {
        return Agenda(agendaWorkoutReservation, agendaConsultingReservation.deleteConsultingReservation(reservation))
    }

    fun deleteWorkoutReservation(reservation: WorkoutReservation): Agenda {
        return Agenda(agendaWorkoutReservation.deleteWorkoutReservation(reservation), agendaConsultingReservation)
    }

    fun retrieveConsultingReservation(): List<ConsultingReservation> {
        return agendaConsultingReservation.consultingReservationList
    }

    fun retrieveWorkoutReservation(): List<WorkoutReservation> {
        return agendaWorkoutReservation.workoutReservationList
    }
}
