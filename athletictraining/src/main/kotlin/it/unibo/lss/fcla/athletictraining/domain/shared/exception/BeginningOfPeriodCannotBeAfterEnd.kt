package it.unibo.lss.fcla.athletictraining.domain.shared.exception

/**
 * Thrown to indicate that the period has and end day that happens before the begin day.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class BeginningOfPeriodCannotBeAfterEnd :
    DomainException("The beginning of a period cannot be after its end.")
