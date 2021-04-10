package it.unibo.lss.fcla.athletictraining.domain.model.workout.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that there is no exercise prepared for a workout at the
 * index provided.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class InvalidExerciseIndex :
    DomainException("No exercise found for workout at the index provided.")
