package it.unibo.lss.fcla.reservation.infrastructure

import it.unibo.lss.fcla.reservation.application.interfaces.CommunicationManager

/**
 * Class to handle the means of communication.
 *
 * It implements the [CommunicationManager] interface exposed by application layer.
 */
class CommunicationManagerImpl : CommunicationManager {
    private val events: ArrayDeque<String> = ArrayDeque()

    /**
     * Method used to pick the first event request from list and returns a string which represents the request.
     */
    override fun pickRequest(): String? {
        return events.removeFirstOrNull()
    }

    /**
     * Method used to publish into the EventBus the request
     */
    override fun publishResult(result: String) {
        println("$result published into the Bus!")
    }
}
