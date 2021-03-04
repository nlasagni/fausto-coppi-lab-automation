package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.common.EventSourcedRepository
import it.unibo.lss.fcla.consulting.common.EventStore
import it.unibo.lss.fcla.consulting.domain.member.Member

class MemberMockRepository(eventStore: EventStore) : EventSourcedRepository<Member>(eventStore) {
}