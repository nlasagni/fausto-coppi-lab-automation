package it.unibo.lss.fcla.consulting.domain.consulting.events

import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingType
import it.unibo.lss.fcla.consulting.domain.consulting.Date
import it.unibo.lss.fcla.consulting.domain.consulting.MemberId
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

/**
 * @author Stefano Braggion
 *
 * Event representing a created consulting summary
 */
data class ConsultingCreatedEvent(
    val consultingId: ConsultingId,
    val memberId: MemberId,
    val consultingDate: Date,
    val freelancerId: FreelancerId,
    val consultingType: ConsultingType,
    val description: String
) : DomainEvent
