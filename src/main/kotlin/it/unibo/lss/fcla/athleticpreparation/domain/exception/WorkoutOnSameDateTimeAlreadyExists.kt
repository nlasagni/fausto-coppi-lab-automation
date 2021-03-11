package it.unibo.lss.fcla.athleticpreparation.domain.exception

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutOnSameDateTimeAlreadyExists :
    Exception("A Workout with same date and time already exists.")
