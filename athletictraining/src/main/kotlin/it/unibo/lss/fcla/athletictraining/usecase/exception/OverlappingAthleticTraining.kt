package it.unibo.lss.fcla.athletictraining.usecase.exception

/**
 * Thrown to indicate that the desired period of an athletic training overlaps with another
 * already activated.
 *
 * @author Nicola Lasagni on 07/04/2021.
 */
class OverlappingAthleticTraining :
    UseCaseException("The desired period of an athletic training overlaps with another already activated.")