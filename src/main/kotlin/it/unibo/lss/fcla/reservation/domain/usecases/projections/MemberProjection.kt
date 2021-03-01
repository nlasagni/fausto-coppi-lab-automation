package it.unibo.lss.fcla.reservation.domain.usecases.projections

import it.unibo.lss.fcla.reservation.common.Event
import it.unibo.lss.fcla.reservation.domain.entities.member.Member
import java.util.UUID

class MemberProjection(val init: Member) {
    constructor() : this(Member(firstName = "", lastName = "", UUID.randomUUID()))

    fun update(event: Event): Member {
        // TODO switch(event)
        return init
    }
}
