package it.unibo.lss.fcla.athleticpreparation.domain.exception

/**
 * @author Nicola Lasagni on 01/03/2021.
 */
class TrainingPlanMustNotOverlap :
        Exception("A TrainingPlan must not overlap with other TrainingPlans.")