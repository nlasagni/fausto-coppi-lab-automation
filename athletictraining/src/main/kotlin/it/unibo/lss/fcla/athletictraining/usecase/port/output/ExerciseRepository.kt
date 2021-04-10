package it.unibo.lss.fcla.athletictraining.usecase.port.output

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId

/**
 * Repository that refers to the Exercise aggregate.
 *
 * @author Nicola Lasagni on 06/04/2021.
 */
interface ExerciseRepository {

    /**
     * Adds the desired [exercise] to this repository.
     * @return The [Exercise] just added.
     */
    fun add(exercise: Exercise): Exercise

    /**
     * Updates the desired [exercise] of this repository.
     * @return The [Exercise] just updated.
     */
    fun update(exercise: Exercise): Exercise

    /**
     * Removes the desired [exercise] from this repository.
     * @return True if the exercise has been removed, false otherwise.
     */
    fun remove(exercise: Exercise): Boolean

    /**
     * Finds the [Exercise] that has the specified [id].
     * @return The [Exercise] found if present.
     */
    fun findById(id: ExerciseId): Exercise?

    /**
     * Finds all the [Exercise]s present in this repository.
     * @return All the [Exercise]s present in this repository.
     */
    fun findAll(): Collection<Exercise>
}
