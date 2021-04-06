package it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.exception

/**
 * Thrown to indicate that for the desired CompletedAthleticTraining the id is missing.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class CompletedAthleticTrainingIdMissing : Exception("CompletedAthleticTraining unique id missing.")
