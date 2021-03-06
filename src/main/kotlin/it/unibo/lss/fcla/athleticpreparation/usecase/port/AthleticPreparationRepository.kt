package it.unibo.lss.fcla.athleticpreparation.usecase.port

import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticPreparation
import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticPreparationId

/**
 * @author Nicola Lasagni on 06/03/2021.
 */
interface AthleticPreparationRepository {

    fun create(athleticPreparation: AthleticPreparation): AthleticPreparation

    fun findById(id: AthleticPreparationId): AthleticPreparation?

    fun findByMemberId(memberId: String): List<AthleticPreparation>?

}