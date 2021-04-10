package it.unibo.lss.fcla.athletictraining.domain.model.exercise.exception

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.DomainException
import java.lang.Exception

/**
 * Thrown to indicate that the Configuration is missing the required gym machine reference.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
class ConfigurationMustBeRelatedToGymMachine :
    DomainException("The gym machine reference of this configuration is missing.")
