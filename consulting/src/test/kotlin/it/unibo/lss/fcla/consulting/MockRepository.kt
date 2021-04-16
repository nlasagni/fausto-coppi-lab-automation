/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting

import it.unibo.lss.fcla.consulting.application.adapters.EventStore
import it.unibo.lss.fcla.consulting.common.EventSourcedRepository
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer

/**
 * @author Stefano Braggion
 *
 * Mock repositories only for testing purpose
 */

/**
 * Represent a mock repository for freelancer aggregate
 */
class FreelancerMockRepository(eventStore: EventStore) : EventSourcedRepository<Freelancer>(eventStore)

/**
 * Represent a mock repository for consulting aggregate
 */
class ConsultingMockRepository(eventStore: EventStore) : EventSourcedRepository<Consulting>(eventStore)
