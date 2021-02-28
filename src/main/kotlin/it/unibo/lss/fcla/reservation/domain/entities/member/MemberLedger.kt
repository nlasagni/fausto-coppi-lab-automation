package it.unibo.lss.fcla.reservation.domain.entities.member

class MemberLedger(private val members: List<Member>) {

    constructor() : this(listOf<Member>())

    /**
     * Returns a [MemberLedger] adding a new [member] into the list of members
     */
    fun addMemberToLedger(member: Member): MemberLedger {
        return MemberLedger(members + member)
    }

    /**
     * Returns a list of [Member]
     */
    fun retrieveAllMembers(): List<Member> {
        return members.toList()
    }
}
