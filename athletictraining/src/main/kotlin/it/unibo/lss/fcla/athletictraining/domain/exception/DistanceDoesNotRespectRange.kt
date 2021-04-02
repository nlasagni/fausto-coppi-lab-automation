package it.unibo.lss.fcla.athletictraining.domain.exception

/**
 * @author Nicola Lasagni on 02/04/2021.
 */
class DistanceDoesNotRespectRange :
    Exception("The distance of an exercise must have values inside allowed range.")
