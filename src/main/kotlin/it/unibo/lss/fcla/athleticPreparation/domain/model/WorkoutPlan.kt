package it.unibo.lss.fcla.athleticPreparation.domain.model

/**
 *
 */
data class WorkoutConfiguration(val duration: Int = 0, val intensity: Int = 0)

/**
 *
 */
interface WorkoutPlanType

/**
 *
 */
class StrenghteningPlanType : WorkoutPlanType

/**
 *
 */
class PostInjuryPlanType : WorkoutPlanType

/**
 *
 */
class WorkoutPlan(val planId: String, val durationInMonths: Int, val type: WorkoutPlanType) {

    private var state: WorkoutPlanStatus = WorkoutPlanStatus.ACTIVE

    private val configurations: MutableMap<String, WorkoutConfiguration> = HashMap()

    /**
     *
     */
    fun isActive() = state == WorkoutPlanStatus.ACTIVE

    /**
     *
     */
    fun addConfiguration(name: String, duration: Int, intensity: Int) {
        configurations[name] = WorkoutConfiguration(duration, intensity)
    }

    /**
     *
     */
    fun getConfigurations(): List<WorkoutConfiguration> = configurations.values.toList()

    /**
     *
     */
    fun getConfiguration(name: String): WorkoutConfiguration? {
        return configurations[name]?.copy()
    }

    /**
     *
     */
    fun removeConfiguration(name: String) {
        configurations.remove(name)
    }

    /**
     *
     */
    fun disable() {
        state = WorkoutPlanStatus.DISABLED
    }

    /**
     *
     */
    fun enable() {
        state = WorkoutPlanStatus.ACTIVE
    }
}

private enum class WorkoutPlanStatus {
    ACTIVE, DISABLED
}
