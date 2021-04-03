package it.unibo.lss.fcla.athletictraining.domain.service

import java.time.LocalDateTime

/**
 * Domain service that checks if the gym is open at a given [LocalDateTime].
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
interface GymOpenChecker {

    /**
     * Checks if the gym is open at the given [dateTime].
     * @return true if the gym is open, false otherwise.
     */
    fun isGymOpenAtDateTime(dateTime: LocalDateTime): Boolean

}