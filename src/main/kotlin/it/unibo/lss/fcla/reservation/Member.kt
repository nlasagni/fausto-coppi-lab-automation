package it.unibo.lss.fcla.reservation

data class Member(
        val firstName: String,
        val lastName: String
) {
    override fun toString(): String = "This member is $firstName $lastName"
}
