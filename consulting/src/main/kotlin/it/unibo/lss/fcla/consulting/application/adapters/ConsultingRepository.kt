/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.application.adapters

import it.unibo.lss.fcla.consulting.common.EventSourcedRepository
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting

/**
 * @author Stefano Braggion
 *
 * Concrete implementation of a [EventSourcedRepository] for [Consulting] aggregate
 */
class ConsultingRepository(eventStore: EventStore) : EventSourcedRepository<Consulting>(eventStore)
