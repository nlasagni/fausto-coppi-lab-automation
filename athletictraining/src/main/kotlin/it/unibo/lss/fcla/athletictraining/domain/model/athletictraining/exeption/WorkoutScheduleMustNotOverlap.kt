package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exercise

/**
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutScheduleMustNotOverlap :
    Exception("A workout schedule must not overlap with another one.")
