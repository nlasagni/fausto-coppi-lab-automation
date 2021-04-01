package it.unibo.lss.fcla.athletictraining.domain.exception

import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.Period

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class PeriodOfPreparationDoesNotMeetMinimumDuration :
    Exception(
        "The period of preparation must last at least " +
            "${Period.minimumPeriodDurationInMonth} months."
    )
