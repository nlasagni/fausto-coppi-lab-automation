package it.unibo.lss.fcla.reservation.domain.entities.member

import java.util.*

class MemberLedger(private val members: List<Member>, val id: UUID) {

    constructor(id: UUID) : this(listOf<Member>(), id)

    /**
     * Returns a [MemberLedger] adding a new [member] into the list of members
     */
    fun addMemberToLedger(member: Member): MemberLedger {
        return MemberLedger(members + member, id)
    }

    /**
     * Returns a list of [Member]
     */
    fun retrieveAllMembers(): List<Member> {
        return members.toList()
    }
}
