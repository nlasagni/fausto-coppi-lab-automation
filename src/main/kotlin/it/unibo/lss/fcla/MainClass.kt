package it.unibo.lss.fcla

import it.unibo.lss.fcla.reservation.application.Controller
import it.unibo.lss.fcla.reservation.domain.usecases.CommandReservationUseCase
import it.unibo.lss.fcla.reservation.domain.usecases.EventStore
import it.unibo.lss.fcla.reservation.domain.usecases.QueryReservationUseCase
import java.util.Calendar
import java.util.UUID

class MainClass {
    fun launchReservationMicroservice() {
        Controller().start()
    }
}

fun main() {
    MainClass().launchReservationMicroservice()
}
