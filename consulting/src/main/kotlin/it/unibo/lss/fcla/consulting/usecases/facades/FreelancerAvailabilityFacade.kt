/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.usecases.facades

import it.unibo.lss.fcla.consulting.domain.freelancer.AvailabilityHours
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Represent a freelancer [AvailabilityHours] facade model
 */
class FreelancerAvailabilityFacade internal constructor(
    val availabilityDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime
) : BaseFacade {

    companion object {

        /**
         * Factory
         */
        fun create(date: LocalDate, hours: AvailabilityHours): FreelancerAvailabilityFacade {

            return FreelancerAvailabilityFacade(
                availabilityDate = date,
                fromTime = hours.fromTime,
                toTime = hours.toTime
            )
        }
    }
}
