package it.unibo.lss.fcla.reservation.application.interfaces

/**
 * Interface to handle the means of communication.
 */
interface CommunicationManager {

    /**
     * Method used to pick event request from list end return it in the form of a string.
     */
    fun pickRequest(): String?

    /**
     * Method used to publish into the EventBus the result of a request
     */
    fun publishResult(result: String)
}
