package it.unibo.lss.fcla.athletictraining.usecase.shared.output

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingSnapshot
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId

/**
 * Repository that persist information about the Active Athletic Training.
 *
 * @author Nicola Lasagni on 06/03/2021.
 */
interface ActiveAthleticTrainingRepository {

    /**
     * Adds the desired [snapshot] to this repository.
     * @return The [ActiveAthleticTraining] just added.
     */
    fun add(snapshot: ActiveAthleticTrainingSnapshot): ActiveAthleticTrainingSnapshot

    /**
     * Updates the desired [snapshot] of this repository.
     * @return The [ActiveAthleticTraining] just updated.
     */
    fun update(snapshot: ActiveAthleticTrainingSnapshot): ActiveAthleticTrainingSnapshot

    /**
     * Removes the desired [snapshot] from this repository.
     * @return True if the athletic training has been removed, false otherwise.
     */
    fun remove(snapshot: ActiveAthleticTrainingSnapshot): Boolean

    /**
     * Finds the [ActiveAthleticTrainingSnapshot] that has the specified [id].
     * @return The [ActiveAthleticTraining] found if present.
     */
    fun findById(id: ActiveAthleticTrainingId): ActiveAthleticTrainingSnapshot?

    /**
     * Finds all the [ActiveAthleticTrainingSnapshot]s that refer to the specified [memberId].
     * @return The collection of [ActiveAthleticTrainingSnapshot]s found.
     */
    fun findAllByMemberId(memberId: MemberId): Collection<ActiveAthleticTrainingSnapshot>
}
