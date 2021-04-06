package it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception

/**
 * Thrown to indicate that the Distance specified does not meet the range requirements.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
class DistanceDoesNotRespectRange :
    Exception("The distance of an exercise must have values inside allowed range.")
