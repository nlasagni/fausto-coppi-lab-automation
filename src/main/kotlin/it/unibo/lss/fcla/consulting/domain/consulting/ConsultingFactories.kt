package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.domain.consulting.events.ConsultingCreatedEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

/**
 *
 */
fun Consulting.Companion.createPhysiotherapyConsulting(consultingId: ConsultingId, memberId: MemberId,
                                                       consultingDate: Date, freelancerId: FreelancerId,
                                                       description: String) : Consulting {
    val consultingAggregate = Consulting(consultingId, memberId)
    consultingAggregate.raiseEvent(
        ConsultingCreatedEvent(consultingId, memberId,
            consultingDate, freelancerId, ConsultingType.PhysioterapyConsulting(), description)
    )

    return consultingAggregate
}

/**
 *
 */
fun Consulting.Companion.createNutritionistConsulting(consultingId: ConsultingId, memberId: MemberId,
                                                       consultingDate: Date, freelancerId: FreelancerId,
                                                       description: String) : Consulting {
    val consultingAggregate = Consulting(consultingId, memberId)
    consultingAggregate.raiseEvent(
        ConsultingCreatedEvent(consultingId, memberId,
            consultingDate, freelancerId, ConsultingType.NutritionConsulting(), description)
    )

    return consultingAggregate
}

/**
 *
 */
fun Consulting.Companion.createAthleticTrainerConsulting(consultingId: ConsultingId, memberId: MemberId,
                                                      consultingDate: Date, freelancerId: FreelancerId,
                                                      description: String) : Consulting {
    val consultingAggregate = Consulting(consultingId, memberId)
    consultingAggregate.raiseEvent(
        ConsultingCreatedEvent(consultingId, memberId,
            consultingDate, freelancerId, ConsultingType.AthleticTrainerConsulting(), description)
    )

    return consultingAggregate
}

/**
 *
 */
fun Consulting.Companion.createBiomechanicalConsulting(consultingId: ConsultingId, memberId: MemberId,
                                                      consultingDate: Date, freelancerId: FreelancerId,
                                                      description: String) : Consulting {
    val consultingAggregate = Consulting(consultingId, memberId)
    consultingAggregate.raiseEvent(
        ConsultingCreatedEvent(consultingId, memberId,
            consultingDate, freelancerId, ConsultingType.BiomechanicsConsulting(), description)
    )

    return consultingAggregate
}
