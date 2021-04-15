/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.entities.reservation

import it.unibo.lss.fcla.reservation.domain.entities.exceptions.FreelancerIdCannotBeEmpty
import java.util.UUID

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Freelancer value object.
 *
 * It is the unique identifier of a Freelancer identified by its [value]
 */
class FreelancerId(val value: UUID) {

    init {
        if (value == UUID(0, 0)) {
            throw FreelancerIdCannotBeEmpty()
        }
    }
}
