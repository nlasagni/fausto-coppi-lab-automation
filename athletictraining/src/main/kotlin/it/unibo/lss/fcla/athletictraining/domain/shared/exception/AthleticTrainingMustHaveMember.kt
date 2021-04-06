package it.unibo.lss.fcla.athletictraining.domain.shared.exception

/**
 * Thrown to indicate that the athletic training is missing a proper member reference.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class AthleticTrainingMustHaveMember :
    Exception("An athletic training must have a Member that performs it.")
