package it.unibo.lss.fcla.athletictraining.usecase.fclat11

import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId

/**
 * Class that represents the request coming from outer layer of
 * deleting an exercise.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class DeleteExerciseRequest(val exerciseId: ExerciseId)
