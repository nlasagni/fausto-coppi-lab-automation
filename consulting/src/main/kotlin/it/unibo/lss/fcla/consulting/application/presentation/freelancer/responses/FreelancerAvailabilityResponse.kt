/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.application.presentation.freelancer.responses

import it.unibo.lss.fcla.consulting.application.presentation.IResponse
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Represent a freelancer availability response
 */
class FreelancerAvailabilityResponse(
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : IResponse {

    /**
     * String representation of the [FreelancerAvailabilityResponse]
     */
    override fun toString(): String =
        "FreelancerAvailabilityResponse(On=$availabilityDate, From=$fromTime, To=$toTime)"
}
