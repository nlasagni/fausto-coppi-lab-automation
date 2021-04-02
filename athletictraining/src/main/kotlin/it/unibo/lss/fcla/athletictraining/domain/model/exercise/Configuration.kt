package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.exception.ConfigurationMustBeRelatedToGymMachine
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId

/**
 * A Value Object representing the configuration of an [Exercise].
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
data class Configuration(
    val gymMachineId: GymMachineId,
    val intensity: Intensity,
    val distance: Distance
) {

    init {
        if (gymMachineId.value.isEmpty()) {
            throw ConfigurationMustBeRelatedToGymMachine()
        }
    }

}
