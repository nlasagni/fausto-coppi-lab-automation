/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.domain.consulting.exceptions.ConsultingException

/**
 * Thrown when a consulting is created with an already existing Id
 */
class ConsultingShouldHaveAUniqueId :
    ConsultingException("A consulting with the same Id already exist")

/**
 * Thrown when the provided consulting Id does not exist
 */
class ConsultingWithGivenIdDoesNotExist :
    ConsultingException("A consulting with the given Id does not exist")

/**
 * Thrown when a freelancer is created with an already existing Id
 */
class FreelancerShouldHaveAUniqueId :
    ConsultingException("A freelancer with the same Id already exist")

/**
 * Thrown when the provided freelancer Id does not exist
 */
class FreelancerWithGivenIdDoesNotExist :
    ConsultingException("A freelancer with the given Id does not exist")

/**
 * Thrown when the provided freelancer has an incompatible role with the requested consulting
 */
class IncompatibleFreelancerRoleForConsulting :
    ConsultingException("The given freelancer role is incompatible for the requested consulting")
