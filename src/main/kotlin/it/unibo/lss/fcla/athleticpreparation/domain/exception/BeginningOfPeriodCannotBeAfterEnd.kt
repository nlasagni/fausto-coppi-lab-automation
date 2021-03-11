package it.unibo.lss.fcla.athleticpreparation.domain.exception

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class BeginningOfPeriodCannotBeAfterEnd :
    Exception("The beginning of a period cannot be after its end.")
