package it.unibo.lss.fcla.reservation.domain.usecases

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CloseWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.CreateWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.DeleteWorkoutReservationEvent
import it.unibo.lss.fcla.reservation.domain.usecases.events.requests.UpdateWorkoutReservationEvent

class WorkoutReservationManager() : Producer {

    private fun closeWorkoutReservation(event: CloseWorkoutReservationEvent): List<Event> {
        /* TODO
        - retrieve res
        - create closedRes
        - remove oldRes from agenda and member
        - add newRes to agenda and member
         */
        return listOf()
    }

    private fun createWorkoutReservation(event: CreateWorkoutReservationEvent): List<Event> {
        /* TODO
        - create res
        - add newRes to agenda and member
         */
        return listOf()
    }

    private fun deleteWorkoutReservation(event: DeleteWorkoutReservationEvent): List<Event> {
        /* TODO
        - retrieve res
        - remove res from agenda and member
         */
        return listOf()
    }

    private fun updateWorkoutReservation(event: UpdateWorkoutReservationEvent): List<Event> {
        /* TODO
        - retrieve consulting
        - update cons
         */
        return listOf()
    }

    override fun produce(event: Event): List<Event> = when (event) {
        is CloseWorkoutReservationEvent -> closeWorkoutReservation(event)
        is CreateWorkoutReservationEvent -> createWorkoutReservation(event)
        is DeleteWorkoutReservationEvent -> deleteWorkoutReservation(event)
        is UpdateWorkoutReservationEvent -> updateWorkoutReservation(event)
        else -> listOf()
    }
}
