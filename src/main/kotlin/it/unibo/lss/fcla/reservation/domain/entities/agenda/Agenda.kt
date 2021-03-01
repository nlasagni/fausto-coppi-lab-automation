package it.unibo.lss.fcla.reservation.domain.entities.agenda

import it.unibo.lss.fcla.reservation.common.ConsultingReservation
import it.unibo.lss.fcla.reservation.common.WorkoutReservation
import java.util.UUID

class Agenda(
    private val agendaWorkoutReservation: AgendaWorkoutReservation,
    private val agendaConsultingReservation: AgendaConsultingReservation,
    val id: UUID
) {

    constructor(id: UUID) : this(AgendaWorkoutReservation(), AgendaConsultingReservation(), id)

    /**
     * Returns a new [Agenda] adding a [reservation] into the list of [ConsultingReservation]
     */
    fun addConsultingReservation(reservation: ConsultingReservation): Agenda {
        return Agenda(
            agendaWorkoutReservation,
            agendaConsultingReservation.addConsultingReservation(reservation),
            id
        )
    }

    /**
     * Returns a new [Agenda] adding a [reservation] into the list of [WorkoutReservation]
     */
    fun addWorkoutReservation(reservation: WorkoutReservation): Agenda {
        return Agenda(
            agendaWorkoutReservation.addWorkoutReservation(reservation),
            agendaConsultingReservation,
            id
        )
    }

    /**
     * Returns a new [Agenda] deleting a [reservation] from the list of [ConsultingReservation]
     */
    fun deleteConsultingReservation(reservation: ConsultingReservation): Agenda {
        return Agenda(
            agendaWorkoutReservation,
            agendaConsultingReservation.deleteConsultingReservation(reservation),
            id
        )
    }

    /**
     * Returns a new [Agenda] deleting a [reservation] from the list of [WorkoutReservation]
     */
    fun deleteWorkoutReservation(reservation: WorkoutReservation): Agenda {
        return Agenda(
            agendaWorkoutReservation.deleteWorkoutReservation(reservation),
            agendaConsultingReservation,
            id
        )
    }

    /**
     * Returns a list of [ConsultingReservation]
     */
    fun retrieveConsultingReservation(): List<ConsultingReservation> {
        return agendaConsultingReservation.consultingReservationList
    }

    /**
     * Returns a list of [WorkoutReservation]
     */
    fun retrieveWorkoutReservation(): List<WorkoutReservation> {
        return agendaWorkoutReservation.workoutReservationList
    }
}
