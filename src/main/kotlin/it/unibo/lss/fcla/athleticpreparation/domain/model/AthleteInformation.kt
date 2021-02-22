package it.unibo.lss.fcla.athleticpreparation.domain.model

interface AthleteInformation

/**
 *
 */
data class HealthInformation(val type: HealthInformationType, val information: String) : AthleteInformation

/**
 *
 */
data class GenericInformation(val information: String) : AthleteInformation

enum class HealthInformationType {
    NUTRITION, PHYSIOTHERAPY
}
