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
 * Thrown when a freelancer availability is created for a specific date, but another one already exist in that date
 */
class FreelancerAvailabilityAlreadyExist : ConsultingException("An availability already exists for the given date")
