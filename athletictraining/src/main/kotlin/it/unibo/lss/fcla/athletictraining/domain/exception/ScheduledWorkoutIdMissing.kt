package it.unibo.lss.fcla.athletictraining.domain.exception

import java.lang.Exception

/**
 * @author Nicola Lasagni on 31/03/2021.
 */
class ScheduledWorkoutIdMissing :
    Exception("The Workout id is missing for this ScheduledWorkout.")