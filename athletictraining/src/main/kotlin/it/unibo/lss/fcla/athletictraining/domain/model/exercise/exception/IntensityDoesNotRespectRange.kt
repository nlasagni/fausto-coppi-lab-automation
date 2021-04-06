package it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception

/**
 * @author Nicola Lasagni on 02/04/2021.
 */
class IntensityDoesNotRespectRange :
    Exception("The intensity of an exercise must have values inside allowed range.")
