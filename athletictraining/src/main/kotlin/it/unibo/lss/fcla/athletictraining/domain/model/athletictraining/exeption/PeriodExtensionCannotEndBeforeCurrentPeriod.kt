package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption

/**
 * @author Nicola Lasagni on 01/03/2021.
 */
class PeriodExtensionCannotEndBeforeCurrentPeriod :
    Exception("The end of the extension of a period cannot occur before the current one.")
