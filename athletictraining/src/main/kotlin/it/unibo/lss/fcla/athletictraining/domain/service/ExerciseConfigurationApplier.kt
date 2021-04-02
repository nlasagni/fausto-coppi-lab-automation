package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration

/**
 * @author Nicola Lasagni on 02/04/2021.
 */
interface ExerciseConfigurationApplier {

    fun applyConfiguration(configuration: Configuration)
}
