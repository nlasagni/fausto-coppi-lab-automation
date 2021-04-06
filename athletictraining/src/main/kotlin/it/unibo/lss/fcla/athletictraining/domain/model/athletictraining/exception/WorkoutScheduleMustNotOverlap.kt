package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exception

/**
 * Thrown to indicate that the desired schedule of a workout overlaps with
 * an existing one.
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutScheduleMustNotOverlap :
    Exception("A workout schedule must not overlap with another one.")
