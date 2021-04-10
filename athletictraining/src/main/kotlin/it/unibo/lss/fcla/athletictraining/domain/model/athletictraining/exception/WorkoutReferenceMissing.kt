package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that a workout reference is missing in order to
 * schedule it.
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
class WorkoutReferenceMissing :
    DomainException("Workout reference is missing so it cannot be scheduled.")
