package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that the specified ScheduledWorkout has not been found by the
 * ActiveAthleticTraining Aggregate Root.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class ScheduledWorkoutNotFound :
    DomainException("The specified scheduled workout has not been found.")
