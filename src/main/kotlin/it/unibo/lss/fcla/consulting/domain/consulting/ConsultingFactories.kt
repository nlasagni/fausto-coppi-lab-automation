package it.unibo.lss.fcla.consulting.domain.consulting

import it.unibo.lss.fcla.consulting.domain.consulting.events.ConsultingCreatedEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

/**
 * Extension method factory for Consulting. Creates a new consulting of type Physiotherapy
 */
fun Consulting.Companion.createPhysiotherapyConsulting(
    consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: Date,
    freelancerId: FreelancerId,
    description: String
): Consulting {
    val consultingAggregate = Consulting(consultingId, memberId)
    consultingAggregate.raiseEvent(
        ConsultingCreatedEvent(
            consultingId,
            memberId,
            consultingDate,
            freelancerId,
            ConsultingType.PhysiotherapyConsulting(),
            description
        )
    )

    return consultingAggregate
}

/**
 * Extension method factory for Consulting. Creates a new consulting of type Nutritionist
 */
fun Consulting.Companion.createNutritionistConsulting(
    consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: Date,
    freelancerId: FreelancerId,
    description: String
): Consulting {
    val consultingAggregate = Consulting(consultingId, memberId)
    consultingAggregate.raiseEvent(
        ConsultingCreatedEvent(
            consultingId,
            memberId,
            consultingDate,
            freelancerId,
            ConsultingType.NutritionConsulting(),
            description
        )
    )

    return consultingAggregate
}

/**
 * Extension method factory for Consulting. Creates a new consulting of type AthleticTrainer
 */
fun Consulting.Companion.createAthleticTrainerConsulting(
    consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: Date,
    freelancerId: FreelancerId,
    description: String
): Consulting {
    val consultingAggregate = Consulting(consultingId, memberId)
    consultingAggregate.raiseEvent(
        ConsultingCreatedEvent(
            consultingId,
            memberId,
            consultingDate,
            freelancerId,
            ConsultingType.AthleticTrainerConsulting(),
            description
        )
    )

    return consultingAggregate
}

/**
 * Extension method factory for Consulting. Creates a new consulting of type Biomechanical
 */
fun Consulting.Companion.createBiomechanicalConsulting(
    consultingId: ConsultingId,
    memberId: MemberId,
    consultingDate: Date,
    freelancerId: FreelancerId,
    description: String
): Consulting {
    val consultingAggregate = Consulting(consultingId, memberId)
    consultingAggregate.raiseEvent(
        ConsultingCreatedEvent(
            consultingId,
            memberId,
            consultingDate,
            freelancerId,
            ConsultingType.BiomechanicalConsulting(),
            description
        )
    )

    return consultingAggregate
}
