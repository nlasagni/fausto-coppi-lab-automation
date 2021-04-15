package it.unibo.lss.fcla.athletictraining.domain.model.workout.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that an id is missing for a workout.
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
class WorkoutIdMissing : DomainException("Workout unique id missing.")
