/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

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
