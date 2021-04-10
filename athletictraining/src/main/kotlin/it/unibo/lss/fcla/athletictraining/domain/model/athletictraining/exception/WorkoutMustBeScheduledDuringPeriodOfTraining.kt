package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException

/**
 * Thrown to indicate that the desired schedule of a workout is out of the
 * period of the related ActiveAthleticTraining.
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
class WorkoutMustBeScheduledDuringPeriodOfTraining :
    DomainException("A Workout must be scheduled in a day inside the desired period of training.")
