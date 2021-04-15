package it.unibo.lss.fcla.athletictraining.adapter.idgenerator

import it.unibo.lss.fcla.athletictraining.usecase.shared.output.IdGenerator

/**
 * An [IdGenerator] that generates id incrementally.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class IncrementalGenerator : IdGenerator {

    private var id = 1L

    override fun generate(): String {
        return id++.toString()
    }
}
