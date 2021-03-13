package it.unibo.lss.fcla.reservation.application.interfaces

interface CommunicationManager {

    fun pickRequest(): String

    fun publishResult(result: String)
}
