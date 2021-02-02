package it.unibo.lss.fcla.athleticPreparation.domain.model

interface AthleteInformation

data class HealthInformation(val type: HealthInformationType, val information: String) : AthleteInformation

data class GenericInformation(val information: String) : AthleteInformation

enum class HealthInformationType {
    NUTRITION, PHYSIOTHERAPY
}