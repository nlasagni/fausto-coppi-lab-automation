package it.unibo.lss.fcla.consulting.models

import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Represents the hours of availability in a day for a freelancer, in a form
 * of a time slot
 *
 */
data class AvailabilityHours(val fromTime: LocalTime, val toTime: LocalTime)