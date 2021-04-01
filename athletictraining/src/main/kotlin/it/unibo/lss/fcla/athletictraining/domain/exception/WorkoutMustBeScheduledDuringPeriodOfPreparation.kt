package it.unibo.lss.fcla.athletictraining.domain.exception

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutMustBeScheduledDuringPeriodOfPreparation :
    Exception("A Workout must be scheduled in a day inside its related period of preparation.")
