package it.unibo.lss.fcla.athletictraining.domain.service.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that the total Duration of workout exercises
 * is lower than or equal to zero.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class WorkoutDurationMustBeGreaterThanZero :
    DomainException("The total workout duration must be greater than zero.")