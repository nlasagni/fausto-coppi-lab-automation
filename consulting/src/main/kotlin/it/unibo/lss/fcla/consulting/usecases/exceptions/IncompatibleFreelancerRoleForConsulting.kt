/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.usecases.exceptions

import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingException

/**
 * @author Stefano Braggion
 *
 * Thrown when the provided freelancer has an incompatible role with the requested consulting
 */
class IncompatibleFreelancerRoleForConsulting :
    ConsultingException("The given freelancer role is incompatible for the requested consulting")
