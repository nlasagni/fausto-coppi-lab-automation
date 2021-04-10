package it.unibo.lss.fcla.athletictraining.domain.shared.exception

/**
 * Class that represents possible exceptions thrown by the Domain layer.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
open class DomainException(message: String) : Exception(message)