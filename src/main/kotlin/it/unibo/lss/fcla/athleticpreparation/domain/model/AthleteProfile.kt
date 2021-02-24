package it.unibo.lss.fcla.athleticpreparation.domain.model

import it.unibo.lss.fcla.athleticpreparation.domain.exception.WorkoutPlanException
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.filter
import kotlin.collections.filterIsInstance
import kotlin.collections.mutableListOf
import kotlin.collections.set
import kotlin.collections.toList

/**
 *
 */
data class MedicalCertificate(val dueDate: Date = Date(), val typeOfCertificate: String = "")

/**
 *
 */
data class YouthSectorAthlete(val firstName: String = "", val lastName: String = "", val birthDate: Date = Date())

/**
 *
 */
class AthleteProfile(val firstName: String, val lastName: String, val birthDate: Date) {

    private val youthSectorAthlete: YouthSectorAthlete = YouthSectorAthlete(firstName, lastName, birthDate)
    private var medicalCertificate: MedicalCertificate = MedicalCertificate()
    private val athleteInformations = mutableListOf<AthleteInformation>()

    private val planList = HashMap<String, WorkoutPlan>()

    /**
     *
     */
    fun createWorkoutPlan(planId: String, duration: Int, type: WorkoutPlanType): WorkoutPlan {
        if (planList.containsKey(planId)) throw WorkoutPlanException("A workout plan with id $planId already exist")
        val workout = WorkoutPlan(planId, duration, type)
        planList[planId] = workout

        return workout
    }

    /**
     *
     */
    fun getWorkoutPlan(planId: String): WorkoutPlan? {
        return planList[planId]
    }

    /**
     *
     */
    fun renewMedicalCertificate(dueDate: Date, typeOfCertificate: String) {
        medicalCertificate = MedicalCertificate(dueDate, typeOfCertificate)
    }

    /**
     *
     */
    fun getMedicalCertificate(): MedicalCertificate {
        return medicalCertificate.copy()
    }

    /**
     *
     */
    fun createNutritionHealthInformation(information: String) {
        athleteInformations.add(HealthInformation(HealthInformationType.NUTRITION, information))
    }

    /**
     *
     */
    fun createPhysiotherapyHealthInformation(information: String) {
        athleteInformations.add(HealthInformation(HealthInformationType.PHYSIOTHERAPY, information))
    }

    /**
     *
     */
    fun createGenericInformation(information: String) {
        athleteInformations.add(GenericInformation(information))
    }

    /**
     *
     */
    fun getNutritionHealthInformations(): List<AthleteInformation> =
        athleteInformations.filterIsInstance<HealthInformation>()
            .filter { it.type == HealthInformationType.NUTRITION }
            .toList()

    /**
     *
     */
    fun getPhysiotherapyHealthInformations(): List<AthleteInformation> =
        athleteInformations.filterIsInstance<HealthInformation>()
            .filter { it.type == HealthInformationType.PHYSIOTHERAPY }
            .toList()

    /**
     *
     */
    fun getGenericInformations(): List<AthleteInformation> =
        athleteInformations.filterIsInstance<GenericInformation>()
            .toList()
}
