package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository

/**
 * An [ExerciseRepository] that stores information in application memory.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class InMemoryExerciseRepository : ExerciseRepository {

    private val inMemoryStorage: MutableMap<ExerciseId, Exercise> = mutableMapOf()

    override fun add(exercise: Exercise): Exercise {
        return addOrUpdate(exercise)
    }

    override fun update(exercise: Exercise): Exercise {
        return addOrUpdate(exercise)
    }

    private fun addOrUpdate(exercise: Exercise): Exercise {
        val id = exercise.id
        inMemoryStorage[id] = exercise
        return exercise
    }

    override fun remove(exercise: Exercise): Boolean {
        return inMemoryStorage.remove(exercise.id) != null
    }

    override fun findById(id: ExerciseId): Exercise? = inMemoryStorage[id]

    override fun findAll(): Collection<Exercise> = inMemoryStorage.values.toList()
}