package it.unibo.lss.fcla.athleticpreparation.domain.model

import io.kotest.core.spec.style.FreeSpec

class WorkoutPlanTests : FreeSpec({

    "test add new configuration to workout plan" - {
        val workout = WorkoutPlan("workout0", 3, PostInjuryPlanType())
        workout.addConfiguration("machine1", 10, 3)
        val conf = workout.getConfiguration("machine1")
        assert(conf != null)
    }

    "test status after disabled workout plan" - {
        val workout = WorkoutPlan("workout0", 3, PostInjuryPlanType())
        workout.disable()
        assert(!workout.isActive())
    }

    "test workout configuration" - {
        val workoutConfiguration = WorkoutConfiguration(5, 1)
        assert(workoutConfiguration.duration == 5 && workoutConfiguration.intensity == 1)
    }

    "test workout configuration value object" - {
        val workoutConfiguration1 = WorkoutConfiguration(1, 2)
        val workoutConfiguration2 = WorkoutConfiguration(3, 4)
        val workoutConfiguration3 = WorkoutConfiguration(1, 2)
        assert((workoutConfiguration1 != workoutConfiguration2) && (workoutConfiguration1 == workoutConfiguration3))
    }
})
