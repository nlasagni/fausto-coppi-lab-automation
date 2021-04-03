package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exercise

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class BeginningOfPeriodCannotBeAfterEnd :
    Exception("The beginning of a period cannot be after its end.")
