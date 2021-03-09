package it.unibo.lss.fcla.consulting.domain.consulting

/**
 * Consulting type as enumeration
 */
sealed class ConsultingType {
    data class PhysiotherapyConsulting(val name: String = "PhysiotherapyConsulting") : ConsultingType()
    data class NutritionConsulting(val name: String = "NutritionConsulting") : ConsultingType()
    data class BiomechanicalConsulting(val name: String = "BiomechanicalConsulting") : ConsultingType()
    data class AthleticTrainerConsulting(val name: String = "AthleticTrainerConsulting") : ConsultingType()
}
