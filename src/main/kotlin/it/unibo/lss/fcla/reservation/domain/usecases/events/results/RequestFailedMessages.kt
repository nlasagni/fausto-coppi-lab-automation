package it.unibo.lss.fcla.reservation.domain.usecases.events.results

object RequestFailedMessages {
    const val reservationNotFound = "The requested reservation was not found."
    const val emptyWorkoutAim = "Unable to create a workout reservation due to empty aim."
    const val emptyConsultingFreelancer = "Unable to create a consulting reservation due to empty freelancer."
    const val pastDateInReservation = "Unable to create a reservation due to insertion of a past date."
    const val noUpdateToCloseReservation = "Update to close reservation are not allowed."
}
