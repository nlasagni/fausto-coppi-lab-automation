package it.unibo.lss.fcla.reservation

/**
 * The membershipCard class that contains the information about the member card
 * @param code The code of each member card
 */
data class MembershipCard(val code: String) {
    override fun toString(): String = "Membership card code: $code"
}
