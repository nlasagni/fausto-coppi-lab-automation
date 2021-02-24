package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveAthleticTrainer
import it.unibo.lss.fcla.athleticpreparation.domain.exception.AthleticPreparationMustHaveMember

/**
 * The AthleticPreparation domain entity.
 *
 * This entity encapsulates all information which belongs to an athletic preparation.
 *
 * @author Nicola Lasagni on 22/02/2021.
 *
 * @param athleticTrainerId The id reference of the athletic trainer who is planning the athletic preparation.
 * @param memberId The id reference of the member for whom the athletic preparation is being planned.
 * @param periodOfPreparation The period of athletic preparation.
 */
class ActiveAthleticPreparation(
        val athleticTrainerId: String,
        val memberId: String,
        val periodOfPreparation: PeriodOfPreparation
) {

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

    private fun generateId(): String =
            "$athleticTrainerId-$memberId-${periodOfPreparation.beginning.dayOfYear}"

}