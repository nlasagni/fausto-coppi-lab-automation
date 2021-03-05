package it.unibo.lss.fcla.athleticpreparation.domain.exception

/**
 * @author Nicola Lasagni on 01/03/2021.
 */
class ExceededMaximumWorkoutDuration :
        Exception("The total duration of exercises cannot exceed maximum workout duration.")
