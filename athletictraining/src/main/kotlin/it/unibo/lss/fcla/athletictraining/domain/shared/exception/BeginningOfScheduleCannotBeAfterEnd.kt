package it.unibo.lss.fcla.athletictraining.domain.shared.exception

/**
 * Thrown to indicate that the schedule has a end time that happens before the start time.
 *
 * @author Nicola Lasagni on 01/04/2021.
 */
class BeginningOfScheduleCannotBeAfterEnd :
    DomainException("The beginning of a Schedule cannot be after its end.")
