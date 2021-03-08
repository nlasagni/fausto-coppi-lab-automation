package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.common.EventSourcedRepository
import it.unibo.lss.fcla.consulting.common.EventStore
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer

/**
 * @author Stefano Braggion
 *
 * Mock repositories only for testing purpose
 */

/**
 *
 */
class FreelancerMockRepository(eventStore: EventStore) : EventSourcedRepository<Freelancer>(eventStore) {
}

/**
 *
 */
class ConsultingMockRepository(eventStore: EventStore) : EventSourcedRepository<Consulting>(eventStore) {
}