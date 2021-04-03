package it.unibo.lss.fcla.athletictraining.domain.model.exercise

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import it.unibo.lss.fcla.athletictraining.domain.exception.NameMustNotBeEmpty
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import org.junit.jupiter.api.assertThrows
import java.time.Duration

/**
 * Tests of the [Exercise] Aggregate Root.
 *
 * @author Nicola Lasagni on 02/04/2021.
 */
class ExerciseTest : FreeSpec({

    lateinit var name: String
    lateinit var durationOfExecution: Duration
    lateinit var durationOfRest: Duration
    lateinit var intensity: Intensity
    lateinit var distance: Distance
    lateinit var configuration: Configuration
    lateinit var validExercise: Exercise

    beforeAny {
        name = "Exercise 1"
        durationOfExecution = Duration.ofHours(1)
        durationOfRest = Duration.ofMinutes(30)
        intensity = Intensity(Intensity.NORMAL)
        distance = Distance(1000)
        configuration = Configuration(
            GymMachineId("1234"),
            intensity,
            distance
        )
        validExercise = Exercise(
            name,
            configuration,
            durationOfExecution,
            durationOfRest
        )
    }

    "An exercise should" - {
        "have a name" - {
            assertThrows<NameMustNotBeEmpty> {
                Exercise("", configuration, durationOfExecution, durationOfRest)
            }
        }
        "allow to be renamed" - {
            val newName = "Exercise 2"
            validExercise.rename(newName)
            val snapshot = validExercise.snapshot()
            snapshot.name.shouldBe(newName)
        }
        "allow to be related to a different gym machine" - {
            val newGymMachineId = GymMachineId("2345")
            validExercise.changeGymMachine(newGymMachineId)
            val snapshot = validExercise.snapshot()
            snapshot.configuration.gymMachineId.shouldBe(newGymMachineId)
        }
        "allow the increment of intensity" - {
            val intensityToIncrement = Intensity()
            validExercise.incrementIntensity(intensityToIncrement)
            val snapshot = validExercise.snapshot()
            snapshot.configuration.intensity.shouldBe(intensity + intensityToIncrement)
        }
        "allow the decrement of intensity" - {
            val intensityToDecrement = Intensity()
            validExercise.decrementIntensity(intensityToDecrement)
            val snapshot = validExercise.snapshot()
            snapshot.configuration.intensity.shouldBe(intensity - intensityToDecrement)
        }
        "allow the increment of distance" - {
            val distanceToIncrement = Distance()
            validExercise.incrementDistance(distanceToIncrement)
            val snapshot = validExercise.snapshot()
            snapshot.configuration.distance.shouldBe(distance + distanceToIncrement)
        }
        "allow the decrement of distance" - {
            val distanceToDecrement = Distance()
            validExercise.decrementDistance(distanceToDecrement)
            val snapshot = validExercise.snapshot()
            snapshot.configuration.distance.shouldBe(distance - distanceToDecrement)
        }
    }
})