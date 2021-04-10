package it.unibo.lss.fcla.athletictraining.domain.model.workout.exception

/**
 * Thrown to indicate that there is no exercise prepared for a workout at the
 * index provided.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class InvalidExerciseIndex : Exception("No exercise found for workout at the index provided.")