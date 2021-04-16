/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.domain.freelancer.exceptions

import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingException

/**
 * @author Stefano Braggion
 *
 * Thrown when a freelancer availability is created with a starting time smaller than end time
 */
class FreelancerAvailabilityNotValidTime :
    ConsultingException("The fromTime of an availability must be smaller than toTime")
