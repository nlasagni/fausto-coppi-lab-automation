package it.unibo.lss.fcla.consulting.eventbus

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

class EventBus : IEventBus {

    private val handlers: MutableList<IEventHandler> = mutableListOf()

    override fun publish(event: DomainEvent) {
        handlers.forEach { it.handle(event) }
    }

    override fun subscribe(eventHandler: IEventHandler) {
        handlers.add(eventHandler)
    }
}