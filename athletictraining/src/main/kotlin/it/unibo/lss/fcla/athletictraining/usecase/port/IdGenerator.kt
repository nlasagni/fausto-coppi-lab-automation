package it.unibo.lss.fcla.athletictraining.usecase.port

/**
 * A generator of unique ids as [String].
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
interface IdGenerator {
    fun generate(): String
}
