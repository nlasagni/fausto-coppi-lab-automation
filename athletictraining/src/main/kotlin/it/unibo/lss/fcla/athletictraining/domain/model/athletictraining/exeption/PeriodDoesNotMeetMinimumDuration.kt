package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.Period

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class PeriodDoesNotMeetMinimumDuration :
    Exception(
        "The period must last at least ${Period.minimumPeriodDurationInMonth} months."
    )
