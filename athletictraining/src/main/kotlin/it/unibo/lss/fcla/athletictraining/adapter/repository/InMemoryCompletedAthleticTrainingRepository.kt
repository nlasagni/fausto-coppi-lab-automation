/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.CompletedAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.CompletedAthleticTrainingRepository

/**
 * A [CompletedAthleticTrainingRepository] that stores information in application memory.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class InMemoryCompletedAthleticTrainingRepository : CompletedAthleticTrainingRepository {

    private val inMemoryStorage: MutableMap<CompletedAthleticTrainingId, CompletedAthleticTraining> =
        mutableMapOf()

    override fun add(completedAthleticTraining: CompletedAthleticTraining): CompletedAthleticTraining {
        inMemoryStorage[completedAthleticTraining.id] = completedAthleticTraining
        return completedAthleticTraining
    }

    override fun findAll(): Collection<CompletedAthleticTraining> = inMemoryStorage.values.toList()
}
