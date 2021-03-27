package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.common.EventSourcedRepository
import it.unibo.lss.fcla.consulting.persistence.EventStore
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer

/**
 * @author Stefano Braggion
 *
 * Mock repositories only for testing purpose
 */

/**
 * Represent a mock repository for freelancer aggregate
 */
class FreelancerMockRepository(eventStore: EventStore) : EventSourcedRepository<Freelancer>(eventStore)

/**
 * Represent a mock repository for consulting aggregate
 */
class ConsultingMockRepository(eventStore: EventStore) : EventSourcedRepository<Consulting>(eventStore)
