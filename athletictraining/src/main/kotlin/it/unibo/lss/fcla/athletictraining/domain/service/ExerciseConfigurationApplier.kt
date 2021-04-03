package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId

/**
 * Domain service that applies a given [Configuration] of an [Exercise] to
 * a gym machine.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
interface ExerciseConfigurationApplier {

    /**
     * Applies the desired [configuration] to the gym machine specified
     * by the [GymMachineId] property of the [configuration].
     * @return true if the operation completed successfully, false otherwise.
     */
    fun applyConfiguration(configuration: Configuration): Boolean

}
