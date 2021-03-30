package it.unibo.lss.fcla.athletictraining.domain.exception

/**
 * @author Nicola Lasagni on 01/03/2021.
 */
class TrainingPlanMustNotOverlap :
    Exception("A TrainingPlan must not overlap with other TrainingPlans.")
