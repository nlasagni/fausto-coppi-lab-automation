package it.unibo.lss.fcla.athleticpreparation.domain.exception

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutMustBeScheduledDuringPeriodOfTraining :
        Exception("A Workout must be in a day inside its related period of training.")