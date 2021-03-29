package it.unibo.lss.fcla.consulting.application.persistence

import it.unibo.lss.fcla.consulting.common.EventSourcedRepository
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer

class FreelancerRepository(eventStore: EventStore) : EventSourcedRepository<Freelancer>(eventStore)
