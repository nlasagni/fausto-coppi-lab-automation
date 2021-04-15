/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.domain.entities.exceptions.AimCannotBeEmpty

/**
 * Aim value object.
 *
 * It is the unique identifier of a workout [value]
 */
data class Aim(val value: String) {

    init {
        if (value.isEmpty()) {
            throw AimCannotBeEmpty()
        }
    }
}
