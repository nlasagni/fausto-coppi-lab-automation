package it.unibo.lss.fcla.athleticpreparation.domain.exceptions

/**
 * @author Nicola Lasagni on 22/02/2021.
 */
class BeginningOfPreparationCannotBeAfterEnd :
        Exception("The beginning of a period of preparation cannot be after its end.")