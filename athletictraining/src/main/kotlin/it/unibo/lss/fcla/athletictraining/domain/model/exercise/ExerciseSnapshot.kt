package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import java.time.Duration

/**
 * A snapshot class used to expose all the relevant information about an [Exercise].
 *
 * @property id The [ExerciseId] to which this snapshot refers.
 * @property name The name of the [Exercise] to which this snapshot refers.
 * @property gymMachineId  The gym machine related to the exercise to which this snapshot refers.
 * @property intensity  The [Intensity] of the exercise to which this snapshot refers.
 * @property distance  The [Distance] of the exercise to which this snapshot refers.
 * @property durationOfExecution The [Duration] of execution of the exercise to which this snapshot refers.
 * @property durationOfRest The [Duration] of rest of the exercise to which this snapshot refers.
 *
 * @author Nicola Lasagni on 03/04/2021.
 */
class ExerciseSnapshot(
    val id: ExerciseId,
    val name: String,
    val gymMachineId: GymMachineId,
    val intensity: Intensity,
    val distance: Distance,
    val durationOfExecution: Duration,
    val durationOfRest: Duration
)
