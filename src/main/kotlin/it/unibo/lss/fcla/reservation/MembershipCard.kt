package it.unibo.lss.fcla.reservation

data class MembershipCard(val code: String) {
    override fun toString(): String = "Membership card code: $code"
}
