package it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that for the desired CompletedAthleticTraining the id is missing.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class CompletedAthleticTrainingIdMissing :
    DomainException("CompletedAthleticTraining unique id missing.")
