package it.unibo.lss.fcla.consulting.application.persistence

import it.unibo.lss.fcla.consulting.common.EventSourcedRepository
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting

class ConsultingRepository(eventStore: EventStore) : EventSourcedRepository<Consulting>(eventStore)