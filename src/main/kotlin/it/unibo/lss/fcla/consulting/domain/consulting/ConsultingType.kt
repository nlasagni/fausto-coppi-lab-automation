package it.unibo.lss.fcla.consulting.domain.consulting

/**
 * Consulting type as enumeration
 */
sealed class ConsultingType {
    data class PhysioterapyConsulting(val name: String = "PhysioterapyConsulting") : ConsultingType()
    data class NutritionConsulting(val name: String = "NutritionConsulting") : ConsultingType()
    data class BiomechanicsConsulting(val name: String = "BiomechanicsConsulting") : ConsultingType()
}