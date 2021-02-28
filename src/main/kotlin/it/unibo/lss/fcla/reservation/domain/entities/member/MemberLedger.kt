package it.unibo.lss.fcla.reservation.domain.entities.member

class MemberLedger(private val members: List<Member>) {

    constructor() : this(listOf<Member>())

    /**
     * This method is used to add a [member] into the list of all members
     */
    fun addMemberToLedger(member: Member): MemberLedger {
        return MemberLedger(members + member)
    }

    fun retrieveAllMembers(): List<Member> {
        return members.toList()
    }
}
