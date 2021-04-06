package it.unibo.lss.fcla.athletictraining.usecase.exception

import java.lang.Exception

/**
 * @author Nicola Lasagni on 07/03/2021.
 */
class AthleticPreparationWithSamePeriodAlreadyExists :
    Exception("An AthleticPreparation with same PeriodOfPreparation already exists.")
