package it.unibo.lss.fcla.reservation

data class Professional(val type: ProfessionalType, val firstName: String, val lastName: String) {
    override fun toString(): String = "Professional figure: [$type, $firstName, $lastName]"
}
