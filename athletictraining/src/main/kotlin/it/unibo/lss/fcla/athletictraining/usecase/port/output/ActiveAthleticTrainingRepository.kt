package it.unibo.lss.fcla.athletictraining.usecase.port.output

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId

/**
 * Repository that refers to the Active Athletic Training aggregate.
 *
 * @author Nicola Lasagni on 06/03/2021.
 */
interface ActiveAthleticTrainingRepository {

    /**
     * Adds the desired [activeAthleticTraining] to this repository.
     * @return The [ActiveAthleticTraining] just added.
     */
    fun add(activeAthleticTraining: ActiveAthleticTraining): ActiveAthleticTraining

    /**
     * Updates the desired [activeAthleticTraining] of this repository.
     * @return The [ActiveAthleticTraining] just updated.
     */
    fun update(activeAthleticTraining: ActiveAthleticTraining): ActiveAthleticTraining

    /**
     * Removes the desired [activeAthleticTraining] from this repository.
     * @return True if the athletic training has been removed, false otherwise.
     */
    fun remove(activeAthleticTraining: ActiveAthleticTraining): Boolean

    /**
     * Finds the [ActiveAthleticTraining] that has the specified [id].
     * @return The [ActiveAthleticTraining] found if present.
     */
    fun findById(id: ActiveAthleticTrainingId): ActiveAthleticTraining?

    /**
     * Finds all the [ActiveAthleticTraining]s that refer to the specified [memberId].
     * @return The collection of [ActiveAthleticTraining]s found if present.
     */
    fun findAllByMemberId(memberId: MemberId): Collection<ActiveAthleticTraining>
}
