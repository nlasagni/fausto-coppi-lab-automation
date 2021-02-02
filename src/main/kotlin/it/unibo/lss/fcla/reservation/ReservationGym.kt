package it.unibo.lss.fcla.reservation

data class ReservationGym(
        val machine: Machine
) {
    override fun toString(): String = "This reservation is for a ${machine.name} machine"
}
