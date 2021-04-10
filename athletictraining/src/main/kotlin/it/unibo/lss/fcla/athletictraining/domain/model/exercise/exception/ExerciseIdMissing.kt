package it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that an id is missing for an Exercise.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class ExerciseIdMissing : DomainException("Exercise unique id missing.")
