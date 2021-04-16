/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.adapter.idgenerator

import it.unibo.lss.fcla.athletictraining.usecase.shared.output.IdGenerator
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
