package it.unibo.lss.fcla.athletictraining.usecase

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.AthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.exception.AthleticPreparationWithSameIdAlreadyExists
import it.unibo.lss.fcla.athletictraining.usecase.exception.AthleticPreparationWithSamePeriodAlreadyExists
import it.unibo.lss.fcla.athletictraining.usecase.port.AthleticPreparationRepository

/**
 * @author Nicola Lasagni on 06/03/2021.
 */
class CreateAthleticPreparation(private val repository: AthleticPreparationRepository) {

    fun create(athleticTraining: AthleticTraining): AthleticTraining {
        val snapshot = athleticTraining.snapshot()
        if (repository.findById(snapshot.id) != null) {
            throw AthleticPreparationWithSameIdAlreadyExists()
        }
        if (repository.findAllByPeriodOfPreparation(snapshot.period).isNotEmpty()) {
            throw AthleticPreparationWithSamePeriodAlreadyExists()
        }
        val athleticPreparationToAdd = AthleticTraining(
            snapshot.athleticTrainerId,
            snapshot.memberId,
            snapshot.purpose,
            snapshot.period
        )
        return repository.add(athleticPreparationToAdd)
    }
}
