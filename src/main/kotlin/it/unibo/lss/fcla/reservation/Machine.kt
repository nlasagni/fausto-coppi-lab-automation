package it.unibo.lss.fcla.reservation

data class Machine(
        val name: String
) {
    override fun toString(): String = "This is a $name machine"
}