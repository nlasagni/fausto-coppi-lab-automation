package it.unibo.lss.fcla.reservation

data class ReservationConsulting(
    val professional: Professional,
    val name: Consulting
) {
    override fun toString(): String = "Reservation consulting $name with $professional"
}
