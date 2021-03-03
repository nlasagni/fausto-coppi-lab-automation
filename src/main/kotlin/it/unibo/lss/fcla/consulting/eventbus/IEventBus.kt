package it.unibo.lss.fcla.consulting.eventbus

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

interface IEventBus {
    fun publish(event: DomainEvent)
    fun subscribe(eventHandler: IEventHandler)
}