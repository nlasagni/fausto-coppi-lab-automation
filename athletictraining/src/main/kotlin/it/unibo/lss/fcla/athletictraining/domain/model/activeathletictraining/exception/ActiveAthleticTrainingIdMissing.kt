package it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that an id is missing for an ActiveAthleticTraining.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class ActiveAthleticTrainingIdMissing :
    DomainException("ActiveAthleticTraining unique id missing.")
