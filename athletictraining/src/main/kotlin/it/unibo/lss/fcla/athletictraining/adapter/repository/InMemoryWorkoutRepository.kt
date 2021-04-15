package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutSnapshot
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * A [WorkoutRepository] that stores information in application memory.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class InMemoryWorkoutRepository : WorkoutRepository {

    private val inMemoryStorage: MutableMap<WorkoutId, WorkoutSnapshot> = mutableMapOf()

    override fun add(snapshot: WorkoutSnapshot): WorkoutSnapshot {
        return addOrUpdate(snapshot)
    }

    override fun update(snapshot: WorkoutSnapshot): WorkoutSnapshot {
        return addOrUpdate(snapshot)
    }

    private fun addOrUpdate(workout: WorkoutSnapshot): WorkoutSnapshot {
        val id = workout.id
        inMemoryStorage[id] = workout
        return workout
    }

    override fun findById(id: WorkoutId): WorkoutSnapshot? = inMemoryStorage[id]

    override fun findAll(): Collection<WorkoutSnapshot> = inMemoryStorage.values.toList()
}
