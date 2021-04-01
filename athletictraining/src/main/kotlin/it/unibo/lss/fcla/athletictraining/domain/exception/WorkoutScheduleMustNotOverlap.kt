package it.unibo.lss.fcla.athletictraining.domain.exception

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutScheduleMustNotOverlap :
    Exception("A workout schedule must not overlap with another one.")
