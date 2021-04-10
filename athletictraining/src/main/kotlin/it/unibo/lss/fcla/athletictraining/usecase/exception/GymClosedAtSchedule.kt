package it.unibo.lss.fcla.athletictraining.usecase.exception

import java.lang.Exception

/**
 * Thrown to indicate that the desired schedule refers to a datetime where the gym is closed.
 *
 * @author Nicola Lasagni on 10/04/2021.
 */
class GymClosedAtSchedule :
    UseCaseException("The provided schedules refers to a datetime where the gym is closed.")