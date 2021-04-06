package it.unibo.lss.fcla.athletictraining.domain.model.workout.exception

import java.lang.Exception

/**
 * Thrown to indicate that an id is missing for a workout.
 *
 * @author Nicola Lasagni on 31/03/2021.
 */
class WorkoutIdMissing :
    Exception("Workout unique id missing.")
