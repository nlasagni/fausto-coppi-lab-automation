package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

/**
 * The purpose that an athletic trainer wants a member to reach through an [AthleticTraining].
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
sealed class Purpose {
    data class AthleticTraining(private val name: String = "Athletic Training") : Purpose()
    data class Strengthening(private val name: String = "Strengthening") : Purpose()
    data class InjuryRecovery(private val name: String = "Injury Recovery") : Purpose()
    data class Discharge(private val name: String = "Discharge") : Purpose()
    data class Recovery(private val name: String = "Recovery") : Purpose()
}
