package it.unibo.lss.fcla.athletictraining.usecase.shared.output

/**
 * A generator of unique ids as [String].
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
interface IdGenerator {
    /**
     * Generates a unique id as [String].
     * @return The generated id.
     */
    fun generate(): String
}
