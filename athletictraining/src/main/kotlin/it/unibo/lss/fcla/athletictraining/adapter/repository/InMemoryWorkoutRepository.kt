package it.unibo.lss.fcla.athletictraining.adapter.repository

import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.WorkoutRepository

/**
 * A [WorkoutRepository] that stores information in application memory.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class InMemoryWorkoutRepository : WorkoutRepository {

    private val inMemoryStorage: MutableMap<WorkoutId, Workout> = mutableMapOf()

    override fun add(workout: Workout): Workout {
        return addOrUpdate(workout)
    }

    override fun update(workout: Workout): Workout {
        return addOrUpdate(workout)
    }

    private fun addOrUpdate(workout: Workout): Workout {
        val id = workout.id
        inMemoryStorage[id] = workout
        return workout
    }

    override fun findById(id: WorkoutId): Workout? = inMemoryStorage[id]

    override fun findAll(): Collection<Workout> = inMemoryStorage.values.toList()
}