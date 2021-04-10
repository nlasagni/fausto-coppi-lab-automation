package it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that the Duration of execution is lower than or equal to zero.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
class DurationMustBeGreaterThanZero :
    DomainException("The duration related to an exercise must be greater than zero.")
