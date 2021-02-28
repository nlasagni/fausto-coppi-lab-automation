package it.unibo.lss.fcla.athleticpreparation.domain.model

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
sealed class Purpose {
    data class Strengthening(private val name: String = "Strengthening") : Purpose()
    data class InjuryRecovery(private val name: String = "Injury Recovery") : Purpose()
    data class Discharge(private val name: String = "Discharge") : Purpose()
    data class Recovery(private val name: String = "Recovery") : Purpose()
}