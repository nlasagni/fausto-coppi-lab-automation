package it.unibo.lss.fcla.athletictraining.domain.shared.exception

/**
 * Thrown to indicate that the provided name is empty.
 *
 * @author Nicola Lasagni on 28/02/2021.
 */
class NameMustNotBeEmpty : DomainException("Name must not be empty.")
