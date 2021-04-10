package it.unibo.lss.fcla.athletictraining.usecase.port.output

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId

/**
 * Repository that refers to the Workout aggregate.
 *
 * @author Nicola Lasagni on 06/04/2021.
 */
interface WorkoutRepository {

    /**
     * Adds the desired [workout] to this repository.
     * @return The [Workout] just added.
     */
    fun add(workout: Workout): Workout

    /**
     * Updates the desired [workout] of this repository.
     * @return The [Workout] just updated.
     */
    fun update(workout: Workout): Workout

    /**
     * Finds the [Workout] that has the specified [id].
     * @return The [Workout] found if present.
     */
    fun findById(id: WorkoutId): Workout?

}