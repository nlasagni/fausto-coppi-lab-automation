package it.unibo.lss.fcla.reservation.presentation

import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationFacade

/**
 * Interface used to group all result type
 */
interface Result

/**
 * Specific implementation of [Result] for a [Request] containing a [message]
 */
class ResultRequestMessage(val message: String) : Result

/**
 * Specific implementation of [Result] for a retrieve [Request] containing a [WorkoutReservationDateFacade] list
 */
class ResultRetrieveWorkoutReservationList(val workoutReservations: List<WorkoutReservationDateFacade>) : Result

/**
 * Specific implementation of [Result] for a retrieve [Request] containing a [ConsultingReservationDateFacade] list
 */
class ResultRetrieveConsultingReservationList(
    val consultingReservations: List<ConsultingReservationDateFacade>
) : Result

/**
 * Specific implementation of [Result] for a retrieve [Request] containing a [WorkoutReservationFacade]
 */
class ResultRetrieveWorkoutReservation(val workoutReservation: WorkoutReservationFacade) : Result

/**
 * Specific implementation of [Result] for a retrieve [Request] containing a [ConsultingReservationFacade]
 */
class ResultRetrieveConsultingReservation(val consultingReservation: ConsultingReservationFacade) : Result
