package it.unibo.lss.fcla.consulting.eventbus

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

interface IEventHandler {
    fun handle(event: DomainEvent)
}