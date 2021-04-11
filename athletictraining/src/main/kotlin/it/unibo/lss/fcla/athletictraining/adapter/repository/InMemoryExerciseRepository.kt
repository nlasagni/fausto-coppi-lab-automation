package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseSnapshot
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ExerciseRepository

/**
 * An [ExerciseRepository] that stores information in application memory.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class InMemoryExerciseRepository : ExerciseRepository {

    private val inMemoryStorage: MutableMap<ExerciseId, ExerciseSnapshot> = mutableMapOf()

    override fun add(snapshot: ExerciseSnapshot): ExerciseSnapshot {
        return addOrUpdate(snapshot)
    }

    override fun update(snapshot: ExerciseSnapshot): ExerciseSnapshot {
        return addOrUpdate(snapshot)
    }

    private fun addOrUpdate(snapshot: ExerciseSnapshot): ExerciseSnapshot {
        val id = snapshot.id
        inMemoryStorage[id] = snapshot
        return snapshot
    }

    override fun remove(snapshot: ExerciseSnapshot): Boolean {
        return inMemoryStorage.remove(snapshot.id) != null
    }

    override fun findById(id: ExerciseId): ExerciseSnapshot? = inMemoryStorage[id]

    override fun findAll(): Collection<ExerciseSnapshot> = inMemoryStorage.values.toList()
}
