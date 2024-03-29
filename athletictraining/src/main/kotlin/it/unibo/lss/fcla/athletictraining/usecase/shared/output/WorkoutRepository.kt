/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.shared.output

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutSnapshot

/**
 * Repository that persist information about Workouts.
 *
 * @author Nicola Lasagni on 06/04/2021.
 */
interface WorkoutRepository {

    /**
     * Adds the desired [workout] to this repository.
     * @return The [Workout] just added.
     */
    fun add(snapshot: WorkoutSnapshot): WorkoutSnapshot

    /**
     * Updates the desired [snapshot] of this repository.
     * @return The [WorkoutSnapshot] just updated.
     */
    fun update(snapshot: WorkoutSnapshot): WorkoutSnapshot

    /**
     * Finds the [WorkoutSnapshot] that has the specified [id].
     * @return The [WorkoutSnapshot] found if present.
     */
    fun findById(id: WorkoutId): WorkoutSnapshot?

    /**
     * Finds all the [WorkoutSnapshot]s present in this repository.
     * @return The collection of [WorkoutSnapshot]s present in this repository.
     */
    fun findAll(): Collection<WorkoutSnapshot>
}
