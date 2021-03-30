package it.unibo.lss.fcla.athletictraining.domain.exception

/**
 * @author Nicola Lasagni on 01/03/2021.
 */
class ExceededMaximumWorkoutDuration :
    Exception("The total duration of exercises cannot exceed maximum workout duration.")
