package it.unibo.lss.fcla.athletictraining.domain.exception

/**
 * @author Nicola Lasagni on 03/03/2021.
 */
class TrainingPlanMustBePreparedDuringPeriodOfPreparation :
    Exception("A TrainingPlan must be prepared for a period inside the AthleticPreparation one.")
