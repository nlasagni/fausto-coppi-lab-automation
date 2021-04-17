/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.shared.output

import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTraining

/**
 * Repository that persist information about Completed Athletic Trainings.
 *
 * @author Nicola Lasagni on 06/04/2021.
 */
interface CompletedAthleticTrainingRepository {

    /**
     * Adds the desired [completedAthleticTraining] to this repository.
     * @return The [CompletedAthleticTraining] just added.
     */
    fun add(completedAthleticTraining: CompletedAthleticTraining): CompletedAthleticTraining

    /**
     * Finds all the [CompletedAthleticTraining]s in this repository.
     * @return The collection of [CompletedAthleticTraining]s found.
     */
    fun findAll(): Collection<CompletedAthleticTraining>
}
