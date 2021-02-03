package it.unibo.lss.fcla.reservation

/**
 * Class used to identify a member
 *
 * @param firstName The name of the member
 * @param lastName The surname of the member
 * @param membershipCard The code of member's card
 */
data class Member(val firstName: String, val lastName: String, val membershipCard: MembershipCard) {
    override fun toString(): String = "Member $firstName $lastName"
}
