package it.unibo.lss.fcla.consulting.models

/**
 * @author Stefano Braggion
 *
 * Represents the availability days of a freelancer in a week
 */
sealed class AvailabilityDay{
    data class Monday(val day: String = "Monday", val hours: AvailabilityHours) : AvailabilityDay()
    data class Tuesday(val day: String = "Tuesday", val hours: AvailabilityHours) : AvailabilityDay()
    data class Wednesday(val day: String = "Wednesday", val hours: AvailabilityHours) : AvailabilityDay()
    data class Thursday(val day: String = "Thursday", val hours: AvailabilityHours) : AvailabilityDay()
    data class Friday(val day: String = "Friday", val hours: AvailabilityHours) : AvailabilityDay()
    data class Saturday(val day: String = "Saturday", val hours: AvailabilityHours) : AvailabilityDay()
    data class Sunday(val day: String = "Sunday", val hours: AvailabilityHours) : AvailabilityDay()
}