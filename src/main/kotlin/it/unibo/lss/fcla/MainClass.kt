package it.unibo.lss.fcla

import it.unibo.lss.fcla.reservation.application.Controller

class MainClass {
    fun launchReservationMicroservice() {
        Controller().start()
    }
}

fun main() {
    MainClass().launchReservationMicroservice()
}
