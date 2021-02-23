package it.unibo.lss.fcla.athleticpreparation.domain.exceptions

import it.unibo.lss.fcla.athleticpreparation.domain.model.PeriodOfPreparation

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class PeriodOfPreparationDoesNotMeetMinimumDuration :
        Exception("The period of preparation must last at least ${PeriodOfPreparation.minimumPeriodDurationInMonth} months.")