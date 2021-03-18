package it.unibo.lss.fcla.reservation.presentation

import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationFacade

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
