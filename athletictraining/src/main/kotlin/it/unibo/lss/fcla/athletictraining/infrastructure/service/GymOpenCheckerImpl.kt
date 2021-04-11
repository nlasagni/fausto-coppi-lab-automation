package it.unibo.lss.fcla.athletictraining.infrastructure.service

import it.unibo.lss.fcla.athletictraining.domain.service.GymOpenChecker
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule

/**
 * Dummy implementation of the [GymOpenChecker] service.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class GymOpenCheckerImpl : GymOpenChecker {
    override fun isGymOpenForSchedule(schedule: Schedule): Boolean {
        return true
    }
}