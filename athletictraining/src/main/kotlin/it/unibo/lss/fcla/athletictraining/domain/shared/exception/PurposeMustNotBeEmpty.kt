package it.unibo.lss.fcla.athletictraining.domain.shared.exception

/**
 * Thrown to indicate that the provided purpose is empty.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class PurposeMustNotBeEmpty : DomainException("Purpose must not be empty.")
