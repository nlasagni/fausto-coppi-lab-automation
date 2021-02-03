package it.unibo.lss.fcla.reservation

/**
 * Class used to identify a gym equipment
 *
 * @param name The name of a specific gym equipment
 */
data class Machine(val name: String) {
    override fun toString(): String = "$name machine"
}
