package it.unibo.lss.fcla.athletictraining.domain.service

import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule

/**
 * Domain service that checks if the gym is open at a given [Schedule].
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
interface GymOpenChecker {

    /**
     * Checks if the gym is open at the given [schedule].
     * @return true if the gym is open, false otherwise.
     */
    fun isGymOpenAtDateTime(schedule: Schedule): Boolean
}
