package it.unibo.lss.fcla.athleticpreparation.domain.exception

import it.unibo.lss.fcla.athleticpreparation.domain.model.PeriodOfPreparation
import it.unibo.lss.fcla.athleticpreparation.domain.model.PeriodOfTraining

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class PeriodOfTrainingDoesNotMeetMinimumDuration :
        Exception("The period of training must last at least ${PeriodOfTraining.minimumPeriodDurationInWeeks} weeks.")