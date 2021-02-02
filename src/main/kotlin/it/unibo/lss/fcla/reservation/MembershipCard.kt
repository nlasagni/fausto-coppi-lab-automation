package it.unibo.lss.fcla.reservation

data class MembershipCard(
        val code: String
) {
    override fun toString(): String = "This membership card code is $code"
}
