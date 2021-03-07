package it.unibo.lss.fcla.athleticpreparation.usecase

import it.unibo.lss.fcla.athleticpreparation.domain.model.AthleticPreparation
import it.unibo.lss.fcla.athleticpreparation.usecase.exception.AthleticPreparationWithSameIdAlreadyExists
import it.unibo.lss.fcla.athleticpreparation.usecase.exception.AthleticPreparationWithSamePeriodAlreadyExists
import it.unibo.lss.fcla.athleticpreparation.usecase.port.AthleticPreparationRepository

/**
 * @author Nicola Lasagni on 06/03/2021.
 */
class CreateAthleticPreparation(private val repository: AthleticPreparationRepository) {

    fun create(athleticPreparation: AthleticPreparation): AthleticPreparation {
        val snapshot = athleticPreparation.snapshot()
        if (repository.findById(snapshot.id) != null) {
            throw AthleticPreparationWithSameIdAlreadyExists()
        }
        if (repository.findAllByPeriodOfPreparation(snapshot.periodOfPreparation).isNotEmpty()) {
            throw AthleticPreparationWithSamePeriodAlreadyExists()
        }
        val athleticPreparationToAdd = AthleticPreparation(
                snapshot.athleticTrainerId,
                snapshot.memberId,
                snapshot.periodOfPreparation
        )
        return repository.add(athleticPreparationToAdd)
    }

}