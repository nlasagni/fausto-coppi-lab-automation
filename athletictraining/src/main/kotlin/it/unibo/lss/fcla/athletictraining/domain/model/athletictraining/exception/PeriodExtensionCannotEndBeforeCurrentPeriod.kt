package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exception

/**
 * Thrown to indicate that the desired period extension ends before the current period of
 * an ActiveAthleticTraining.
 *
 * @author Nicola Lasagni on 01/03/2021.
 */
class PeriodExtensionCannotEndBeforeCurrentPeriod :
    Exception("The end of the extension of a period cannot occur before the current one.")
