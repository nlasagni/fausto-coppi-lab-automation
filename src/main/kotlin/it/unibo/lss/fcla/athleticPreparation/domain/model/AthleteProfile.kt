package it.unibo.lss.fcla.athleticPreparation.domain.model

import it.unibo.lss.fcla.athleticPreparation.domain.exceptions.WorkoutPlanException
import java.util.Date

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
    fun createWorkoutPlan(planId: String, duration: Int, type: WorkoutPlanType) {
        if (planList.containsKey(planId)) throw WorkoutPlanException("A workout plan with id $planId already exist")
        planList[planId] = WorkoutPlan(planId, duration, type)
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
    fun getNutritionHealthInformations(): List<AthleteInformation> = athleteInformations.filterIsInstance<HealthInformation>()
        .filter { it.type == HealthInformationType.NUTRITION }
        .toList()

    /**
     *
     */
    fun getPhysiotherapyHealthInformations(): List<AthleteInformation> = athleteInformations.filterIsInstance<HealthInformation>()
        .filter { it.type == HealthInformationType.PHYSIOTHERAPY }
        .toList()

    /**
     *
     */
    fun getGenericInformations(): List<AthleteInformation> = athleteInformations.filterIsInstance<GenericInformation>().toList()
}
