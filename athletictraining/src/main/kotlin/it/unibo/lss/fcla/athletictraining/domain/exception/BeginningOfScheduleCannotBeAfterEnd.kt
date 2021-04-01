package it.unibo.lss.fcla.athletictraining.domain.exception

/**
 * @author Nicola Lasagni on 01/04/2021.
 */
class BeginningOfScheduleCannotBeAfterEnd :
    Exception("The beginning of a Schedule cannot be after its end.")