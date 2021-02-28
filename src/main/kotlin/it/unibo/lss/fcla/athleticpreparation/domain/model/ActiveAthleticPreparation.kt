package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveAthleticTrainer
import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveMember

/**
 * The AthleticPreparation domain entity.
 *
 * This entity encapsulates all information which belongs to an athletic preparation.
 *
 * @property athleticTrainerId The id reference of the athletic trainer who is planning the athletic preparation.
 * @property memberId The id reference of the member for whom the athletic preparation is being planned.
 * @property periodOfPreparation The period of athletic preparation. See [PeriodOfPreparation].
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class ActiveAthleticPreparation(
        val athleticTrainerId: String,
        val memberId: String,
        val periodOfPreparation: PeriodOfPreparation
) {

    /** The id of the athletic preparation. */
    private val id: String

    init {
        if (athleticTrainerId.isEmpty()) {
            throw AthleticPreparationMustHaveAthleticTrainer()
        }
        if (memberId.isEmpty()) {
            throw AthleticPreparationMustHaveMember()
        }
        id = generateId()
    }

    /**
     * Returns a unique id of the athletic preparation which will be stored
     * into the [id] private property.
     */
    private fun generateId(): String =
            "$athleticTrainerId-$memberId-${periodOfPreparation.beginning.dayOfYear}"

}