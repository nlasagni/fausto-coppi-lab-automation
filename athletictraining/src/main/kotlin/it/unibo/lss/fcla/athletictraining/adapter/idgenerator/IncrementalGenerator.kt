package it.unibo.lss.fcla.athletictraining.adapter.idgenerator

import it.unibo.lss.fcla.athletictraining.usecase.shared.output.IdGenerator

/**
 * @author Nicola Lasagni on 11/04/2021.
 */
class IncrementalGenerator : IdGenerator {

    private var id = 0L

    override fun generate(): String {
        id++
        return id.toString()
    }
}
