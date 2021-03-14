package it.unibo.lss.fcla.athleticpreparation.usecase.port

import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticPreparation
import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticPreparationId
import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticTrainerId
import it.unibo.lss.fcla.athleticpreparation.domain.model.MemberId
import it.unibo.lss.fcla.athleticpreparation.domain.model.PeriodOfPreparation

/**
 * @author Nicola Lasagni on 06/03/2021.
 */
interface AthleticPreparationRepository {

    fun add(athleticPreparation: AthleticPreparation): AthleticPreparation

    fun update(athleticPreparation: AthleticPreparation): AthleticPreparation

    fun findById(id: AthleticPreparationId): AthleticPreparation?

    fun findAllByAthleticTrainerId(athleticTrainerId: AthleticTrainerId): List<AthleticPreparation>

    fun findAllByMemberId(memberId: MemberId): List<AthleticPreparation>

    fun findAllByPeriodOfPreparation(periodOfPreparation: PeriodOfPreparation): List<AthleticPreparation>
}
