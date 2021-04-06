package it.unibo.lss.fcla.athletictraining.adapter.idgenerator

import it.unibo.lss.fcla.athletictraining.usecase.port.IdGenerator
import java.util.UUID

/**
 * An [IdGenerator] that generates random [UUID]s.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class UuidGenerator : IdGenerator {
    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}
