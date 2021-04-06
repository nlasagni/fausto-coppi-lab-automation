package it.unibo.lss.fcla.athletictraining.domain.shared.exception

/**
 * Thrown to indicate that the athletic training is missing a proper athletic trainer reference.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class AthleticTrainingMustHaveAthleticTrainer :
    Exception("An athletic training must have an Athletic Trainer that prepares it.")
