package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservationEvent

class WorkoutReservationManager() : Producer {

    private fun closeWorkoutReservation(): List<Event> {
        return listOf()
    }

    private fun createWorkoutReservation(): List<Event> {
        return listOf()
    }

    private fun deleteWorkoutReservation(): List<Event> {
        return listOf()
    }

    private fun updateWorkoutReservation(): List<Event> {
        return listOf()
    }

    override fun produce(event: Event): List<Event> = when (event) {
        is CloseWorkoutReservationEvent -> closeWorkoutReservation()
        is CreateWorkoutReservationEvent -> createWorkoutReservation()
        is DeleteWorkoutReservationEvent -> deleteWorkoutReservation()
        is UpdateWorkoutReservationEvent -> updateWorkoutReservation()
        else -> listOf()
    }
}
