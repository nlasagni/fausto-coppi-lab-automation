/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.domain.exceptions

/**
 * Thrown when a consulting summary is created without a description
 */
class ConsultingSummaryDescriptionCannotBeEmpty :
    ConsultingException("A consulting summary must have a description")

/**
 * Thrown when a consulting summary is created without a valid Freelancer Id
 */
class ConsultingSummaryMustHaveAValidFreelancer :
    ConsultingException("A consulting summary must have a valid freelancer")
