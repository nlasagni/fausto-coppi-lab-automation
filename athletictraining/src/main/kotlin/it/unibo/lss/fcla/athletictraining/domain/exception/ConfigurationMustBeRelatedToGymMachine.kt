package it.unibo.lss.fcla.athletictraining.domain.exception

import java.lang.Exception

/**
 * @author Nicola Lasagni on 02/04/2021.
 */
class ConfigurationMustBeRelatedToGymMachine :
    Exception("The gym machine reference of this configuration is missing.")