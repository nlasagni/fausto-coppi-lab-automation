package it.unibo.lss.fcla.reservation

/**
 * Class that represents the professional figure for a consulting
 *
 * @param type The type of fìprofessional figure
 * @param firstName First name of a professional
 * @param lastName Surname of a professional
 */
data class Professional(val type: ProfessionalType, val firstName: String, val lastName: String) {
    override fun toString(): String = "Professional figure: [$type, $firstName, $lastName]"
}
