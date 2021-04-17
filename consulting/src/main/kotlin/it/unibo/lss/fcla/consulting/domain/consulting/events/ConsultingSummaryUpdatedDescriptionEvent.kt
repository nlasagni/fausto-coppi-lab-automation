/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.domain.consulting.events

import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.interfaces.DomainEvent

/**
 * @author Stefano Braggion
 *
 * Event representing an updated consulting summary description
 */
data class ConsultingSummaryUpdatedDescriptionEvent(
    val consultingId: ConsultingId,
    val description: String
) : DomainEvent
