package it.unibo.lss.fcla.athleticPreparation.domain.builders

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticPreparation.domain.model.WorkoutConfiguration

class WorkoutBuilderTests : FreeSpec({

    beforeTest {
        println("Init test for WorkoutBuilders")
    }

    "test workout configuration creation builder" - {
        val conf = workoutConfiguration {
            duration = 2
            intensity = 1
        }
        val conf2 = WorkoutConfiguration(2, 1)

        assert(conf == conf2)
    }
})
