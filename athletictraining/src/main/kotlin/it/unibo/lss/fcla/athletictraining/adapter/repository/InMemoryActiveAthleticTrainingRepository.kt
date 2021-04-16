/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingSnapshot
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository

/**
 * An [ActiveAthleticTrainingRepository] that stores information in application memory.
 *
 * @author Nicola Lasagni on 07/03/2021.
 */
class InMemoryActiveAthleticTrainingRepository : ActiveAthleticTrainingRepository {

    private val inMemoryStorage: MutableMap<ActiveAthleticTrainingId, ActiveAthleticTrainingSnapshot> =
        mutableMapOf()

    override fun add(snapshot: ActiveAthleticTrainingSnapshot): ActiveAthleticTrainingSnapshot {
        return addOrUpdate(snapshot)
    }

    override fun update(snapshot: ActiveAthleticTrainingSnapshot): ActiveAthleticTrainingSnapshot {
        return addOrUpdate(snapshot)
    }

    override fun remove(snapshot: ActiveAthleticTrainingSnapshot): Boolean {
        return inMemoryStorage.remove(snapshot.id) != null
    }

    private fun addOrUpdate(activeAthleticTraining: ActiveAthleticTrainingSnapshot): ActiveAthleticTrainingSnapshot {
        val id = activeAthleticTraining.id
        inMemoryStorage[id] = activeAthleticTraining
        return activeAthleticTraining
    }

    override fun findById(id: ActiveAthleticTrainingId): ActiveAthleticTrainingSnapshot? = inMemoryStorage[id]

    override fun findAllByMemberId(memberId: MemberId): List<ActiveAthleticTrainingSnapshot> {
        return inMemoryStorage.values.filter { it.member == memberId }
    }
}
