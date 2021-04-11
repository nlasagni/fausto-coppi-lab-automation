package it.unibo.lss.fcla.athletictraining.infrastructure.ui

import java.lang.Exception

/**
 * Thrown to indicate that an input read by the view is not correct.
 *
 * @property message The description of the exception.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class ViewValidationException(message: String) : Exception(message)
