/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.shared

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.PurposeMustNotBeEmpty

/**
 * The purpose that an athletic trainer wants a member to reach
 * through an athletic training.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
data class Purpose(val description: String) {

    init {
        if (description.isEmpty()) {
            throw PurposeMustNotBeEmpty()
        }
    }
}
