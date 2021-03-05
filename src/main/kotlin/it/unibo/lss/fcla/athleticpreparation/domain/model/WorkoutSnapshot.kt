package it.unibo.lss.fcla.athleticpreparation.domain.model

import java.time.LocalDate
import java.time.LocalTime

/**
 * A snapshot class used to expose all the information about a [Workout].
 *
 * @author Nicola Lasagni on 04/03/2021.
 */
class WorkoutSnapshot(
    val name: String,
    val day: LocalDate,
    val time: LocalTime,
    val exercises: List<Exercise>
)