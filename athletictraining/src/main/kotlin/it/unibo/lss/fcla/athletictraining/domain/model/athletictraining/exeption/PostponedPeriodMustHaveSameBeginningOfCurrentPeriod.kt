package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exercise

import java.lang.Exception

/**
 * @author Nicola Lasagni on 01/04/2021.
 */
class PostponedPeriodMustHaveSameBeginningOfCurrentPeriod :
    Exception("The beginning of the extension of a period must be the same of the current one.")
