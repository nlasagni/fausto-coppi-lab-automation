/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.shared.output

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseSnapshot

/**
 * Repository that persist information about e Exercises.
 *
 * @author Nicola Lasagni on 06/04/2021.
 */
interface ExerciseRepository {

    /**
     * Adds the desired [snapshot] to this repository.
     * @return The [Exercise] just added.
     */
    fun add(snapshot: ExerciseSnapshot): ExerciseSnapshot

    /**
     * Updates the desired [snapshot] of this repository.
     * @return The [ExerciseSnapshot] just updated.
     */
    fun update(snapshot: ExerciseSnapshot): ExerciseSnapshot

    /**
     * Removes the desired [snapshot] from this repository.
     * @return True if the exercise has been removed, false otherwise.
     */
    fun remove(snapshot: ExerciseSnapshot): Boolean

    /**
     * Finds the [ExerciseSnapshot] that has the specified [id].
     * @return The [ExerciseSnapshot] found if present.
     */
    fun findById(id: ExerciseId): ExerciseSnapshot?

    /**
     * Finds all the [ExerciseSnapshot]s present in this repository.
     * @return All the [ExerciseSnapshot]s present in this repository.
     */
    fun findAll(): Collection<ExerciseSnapshot>
}
