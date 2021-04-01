package it.unibo.lss.fcla.consulting.application.adapters

import it.unibo.lss.fcla.consulting.common.EventSourcedRepository
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting

/**
 * @author Stefano Braggion
 *
 * Concrete implementation of a [EventSourcedRepository] for [Consulting] aggregate
 */
class ConsultingRepository(eventStore: EventStore) : EventSourcedRepository<Consulting>(eventStore)
