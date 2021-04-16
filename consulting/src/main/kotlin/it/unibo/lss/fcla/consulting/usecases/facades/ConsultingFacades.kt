/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.usecases.facades

import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingType
import it.unibo.lss.fcla.consulting.domain.consulting.MemberId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import java.time.LocalDate

/**
 * @author Stefano Braggion
 */

/**
 * Represent a [Consulting] facade model
 */
class ConsultingFacade internal constructor(
    val consultingId: ConsultingId,
    val memberId: MemberId,
    val consultingDate: LocalDate,
    val freelancerId: FreelancerId,
    val consultingType: ConsultingType,
    val description: String
) : BaseFacade {

    companion object {

        /**
         * Factory
         */
        fun create(consulting: Consulting): ConsultingFacade {
            return ConsultingFacade(
                consultingId = consulting.aggregateId,
                memberId = consulting.getMemberId(),
                consultingDate = consulting.getConsultingSummary().consultingDate,
                freelancerId = consulting.getConsultingSummary().freelancerId,
                consultingType = consulting.getConsultingSummary().consultingType,
                description = consulting.getSummaryDescription()
            )
        }
    }
}

/**
 * Represent a consulting error facade
 */
class ConsultingErrorFacade internal constructor(
    val message: String
) : BaseFacade {

    companion object {

        /**
         * Factory
         */
        fun create(error: String): ConsultingErrorFacade {
            return ConsultingErrorFacade(
                message = error
            )
        }
    }
}
