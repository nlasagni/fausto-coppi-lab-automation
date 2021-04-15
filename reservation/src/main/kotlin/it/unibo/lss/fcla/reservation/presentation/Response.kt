/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.presentation

import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationFacade

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

/**
 * Interface used to group all result type
 */
interface Response

/**
 * Specific implementation of [Response] for a [Request] containing a [message]
 */
class ResponseRequestMessage(val message: String) : Response

/**
 * Specific implementation of [Response] for a retrieve [Request] containing a [WorkoutReservationDateFacade] list
 */
class ResponseRetrieveWorkoutReservationList(val workoutReservations: List<WorkoutReservationDateFacade>) : Response

/**
 * Specific implementation of [Response] for a retrieve [Request] containing a [ConsultingReservationDateFacade] list
 */
class ResponseRetrieveConsultingReservationList(
    val consultingReservations: List<ConsultingReservationDateFacade>
) : Response

/**
 * Specific implementation of [Response] for a retrieve [Request] containing a [WorkoutReservationFacade]
 */
class ResponseRetrieveWorkoutReservation(val workoutReservation: WorkoutReservationFacade) : Response

/**
 * Specific implementation of [Response] for a retrieve [Request] containing a [ConsultingReservationFacade]
 */
class ResponseRetrieveConsultingReservation(val consultingReservation: ConsultingReservationFacade) : Response
