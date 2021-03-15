package it.unibo.lss.fcla.reservation

import it.unibo.lss.fcla.reservation.application.Controller

class InteractiveReservationMicroserviceStarterClass {
    fun launchReservationMicroservice() {
        Controller().start()
    }
}

fun main() {
    InteractiveReservationMicroserviceStarterClass().launchReservationMicroservice()
}
