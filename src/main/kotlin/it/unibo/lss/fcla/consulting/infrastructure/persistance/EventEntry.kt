package it.unibo.lss.fcla.consulting.infrastructure.persistance

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

/**
 * @author Stefano Braggion
 *
 *
 */
data class EventEntry(val event: DomainEvent, val version: Int)
