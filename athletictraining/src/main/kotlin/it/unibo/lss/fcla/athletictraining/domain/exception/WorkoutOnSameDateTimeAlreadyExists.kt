package it.unibo.lss.fcla.athletictraining.domain.exception

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutOnSameDateTimeAlreadyExists :
    Exception("A Workout with same date and time already exists.")
