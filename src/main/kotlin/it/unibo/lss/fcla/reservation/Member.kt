package it.unibo.lss.fcla.reservation

data class Member(val firstName: String, val lastName: String, val membershipCard: MembershipCard) {
    override fun toString(): String = "Member $firstName $lastName"
}
