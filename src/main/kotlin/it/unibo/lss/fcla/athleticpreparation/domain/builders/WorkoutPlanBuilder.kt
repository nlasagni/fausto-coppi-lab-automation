package it.unibo.lss.fcla.athleticpreparation.domain.builders

import it.unibo.lss.fcla.athleticpreparation.domain.model.PostInjuryPlanType
import it.unibo.lss.fcla.athleticpreparation.domain.model.WorkoutConfiguration
import it.unibo.lss.fcla.athleticpreparation.domain.model.WorkoutPlanType

class WorkoutConfigurationBuilder {
    var duration: Int = 0
    var intensity: Int = 0

    fun build(): WorkoutConfiguration {
        return WorkoutConfiguration(duration, intensity)
    }
}

class WorkoutPlanBuilder {
    var planId: String = ""
    var durationInMonths: Int = 1
    var type: WorkoutPlanType = PostInjuryPlanType()

    private val configurations = HashMap<String, WorkoutConfiguration>()
    private val configurationBuilder = WorkoutConfigurationBuilder()

    // nested builder
    fun workoutConfiguration(init: WorkoutConfigurationBuilder.() -> Unit) {
        configurationBuilder.init()
    }
}

fun workoutConfiguration(init: WorkoutConfigurationBuilder.() -> Unit): WorkoutConfiguration {
    val builder = WorkoutConfigurationBuilder()
    builder.init()
    return builder.build()
}
