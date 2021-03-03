package it.unibo.lss.fcla.consulting.common

import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent

class EventBus : IEventBus {
    override fun publish(event: DomainEvent) {
        TODO("Not yet implemented")
    }

    override fun subscribe(eventHandler: IEventHandler) {
        TODO("Not yet implemented")
    }
}