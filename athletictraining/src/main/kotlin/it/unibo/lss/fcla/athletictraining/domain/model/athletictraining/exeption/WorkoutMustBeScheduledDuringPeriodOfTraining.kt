package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exercise

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutMustBeScheduledDuringPeriodOfTraining :
    Exception("A Workout must be scheduled in a day inside the desired period of training.")
