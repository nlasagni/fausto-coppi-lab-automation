/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.common.IRepository
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingType
import it.unibo.lss.fcla.consulting.domain.consulting.MemberId
import it.unibo.lss.fcla.consulting.domain.consulting.createAthleticTrainerConsulting
import it.unibo.lss.fcla.consulting.domain.consulting.createBiomechanicalConsulting
import it.unibo.lss.fcla.consulting.domain.consulting.createNutritionistConsulting
import it.unibo.lss.fcla.consulting.domain.consulting.createPhysiotherapyConsulting
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerRole
import it.unibo.lss.fcla.consulting.domain.interfaces.DomainEvent
import it.unibo.lss.fcla.consulting.usecases.consulting.exceptions.ConsultingShouldHaveAUniqueId
import it.unibo.lss.fcla.consulting.usecases.consulting.exceptions.ConsultingWithGivenIdDoesNotExist
import it.unibo.lss.fcla.consulting.usecases.freelancer.exceptions.FreelancerWithGivenIdDoesNotExist
import it.unibo.lss.fcla.consulting.usecases.freelancer.exceptions.IncompatibleFreelancerRoleForConsulting
import it.unibo.lss.fcla.consulting.usecases.consulting.facades.ConsultingFacade
import java.time.LocalDate

/**
 * @author Stefano Braggion
 *
 * This class contains all the use cases identified in the analysis phase, which allow to interact with
 * behaviour and data of the [Consulting] aggregate.
 *
 */
class ConsultingUseCases(
    private val repository: IRepository<Consulting>,
    private val freelancerRepository: IRepository<Freelancer>,
    private val presenter: IPresenter
) {

    /**
     * FCLAC-2 Manage Consulting Summaries (Examine single summary)
     */
    fun examineConsultingSummaries(consultingId: ConsultingId): Consulting {
        return Consulting.rehydrateConsulting(consultingId, repository.getById(consultingId))
    }

    /**
     * FCLAC-1 Receive Consulting
     */
    fun receivePhysiotherapyConsulting(
        consultingId: ConsultingId,
        memberId: MemberId,
        consultingDate: LocalDate,
        freelancerId: FreelancerId,
        description: String
    ): Consulting {

        if (!freelancerExist(freelancerId)) {
            throw FreelancerWithGivenIdDoesNotExist()
        }

        if (!checkFreelancerRole(freelancerId, ConsultingType.PhysiotherapyConsulting())) {
            throw IncompatibleFreelancerRoleForConsulting()
        }

        /**
         * create a new physiotherapy consulting
         */
        if (repository.getById(consultingId).count() > 0) {
            throw ConsultingShouldHaveAUniqueId()
        }
        val consulting = Consulting.createPhysiotherapyConsulting(
            consultingId,
            memberId,
            consultingDate,
            freelancerId,
            description
        )

        repository.save(consulting)

        presenter.onResult(ConsultingFacade.create(consulting))

        return consulting
    }

    /**
     * FCLAC-1 Receive Consulting
     */
    fun receiveNutritionistConsulting(
        consultingId: ConsultingId,
        memberId: MemberId,
        consultingDate: LocalDate,
        freelancerId: FreelancerId,
        description: String
    ): Consulting {

        if (!freelancerExist(freelancerId)) {
            throw FreelancerWithGivenIdDoesNotExist()
        }

        if (!checkFreelancerRole(freelancerId, ConsultingType.NutritionConsulting())) {
            throw IncompatibleFreelancerRoleForConsulting()
        }

        /**
         * create a new nutritionist consulting
         */
        if (repository.getById(consultingId).count() > 0) {
            throw ConsultingShouldHaveAUniqueId()
        }
        val consulting = Consulting.createNutritionistConsulting(
            consultingId,
            memberId,
            consultingDate,
            freelancerId,
            description
        )

        repository.save(consulting)

        presenter.onResult(ConsultingFacade.create(consulting))

        return consulting
    }

    /**
     * FCLAC-1 Receive Consulting
     */
    fun receiveBiomechanicalConsulting(
        consultingId: ConsultingId,
        memberId: MemberId,
        consultingDate: LocalDate,
        freelancerId: FreelancerId,
        description: String
    ): Consulting {

        if (!freelancerExist(freelancerId)) {
            throw FreelancerWithGivenIdDoesNotExist()
        }

        if (!checkFreelancerRole(freelancerId, ConsultingType.BiomechanicalConsulting())) {
            throw IncompatibleFreelancerRoleForConsulting()
        }

        /**
         * create a new biomechanical consulting
         */
        if (repository.getById(consultingId).count() > 0) {
            throw ConsultingShouldHaveAUniqueId()
        }

        val consulting = Consulting.createBiomechanicalConsulting(
            consultingId,
            memberId,
            consultingDate,
            freelancerId,
            description
        )

        repository.save(consulting)

        presenter.onResult(ConsultingFacade.create(consulting))

        return consulting
    }

    /**
     * FCLAC-1 Receive Consulting
     */
    fun receiveAthleticTrainerConsulting(
        consultingId: ConsultingId,
        memberId: MemberId,
        consultingDate: LocalDate,
        freelancerId: FreelancerId,
        description: String
    ): Consulting {

        if (!freelancerExist(freelancerId)) {
            throw FreelancerWithGivenIdDoesNotExist()
        }

        if (!checkFreelancerRole(freelancerId, ConsultingType.AthleticTrainerConsulting())) {
            throw IncompatibleFreelancerRoleForConsulting()
        }

        /**
         * create a new biomechanical consulting
         */
        if (repository.getById(consultingId).count() > 0) {
            throw ConsultingShouldHaveAUniqueId()
        }

        val consulting = Consulting.createAthleticTrainerConsulting(
            consultingId,
            memberId,
            consultingDate,
            freelancerId,
            description
        )

        repository.save(consulting)

        presenter.onResult(ConsultingFacade.create(consulting))

        return consulting
    }

    /**
     * FCLAC-2 Manage Consulting Summaries (Update)
     */
    fun updateConsultingSummary(consultingId: ConsultingId, description: String): Consulting {

        /**
         * update the consulting with given id
         */
        if (repository.getById(consultingId).count() == 0) {
            throw ConsultingWithGivenIdDoesNotExist()
        }

        val consulting = Consulting.rehydrateConsulting(consultingId, repository.getById(consultingId))
        consulting.updateSummaryDescription(description)
        repository.save(consulting)

        presenter.onResult(ConsultingFacade.create(consulting))

        return consulting
    }

    /**
     * FCLAC-3 Retrieve all the summaries for a member
     */
    fun retrieveProfile(memberId: MemberId): List<Consulting> {
        val events: Map<AggregateId, List<DomainEvent>> = repository.getAllEvents()
        val entityList = mutableListOf<Consulting>()

        events.forEach {
            entityList += Consulting.rehydrateConsulting(it.key, it.value.toList())
        }

        val entries = entityList.filter { it.getMemberId() == memberId }

        entries.forEach {
            presenter.onResult(ConsultingFacade.create(it))
        }

        return entries
    }

    /**
     * Utility method that check if the given [freelancerId] exist
     */
    private fun freelancerExist(freelancerId: FreelancerId) = freelancerRepository.getById(freelancerId).count() > 0

    /**
     * Method that checks if the requested consulting is compatible with the freelancer role
     */
    private fun checkFreelancerRole(freelancerId: FreelancerId, consultingType: ConsultingType): Boolean {
        val freelancer = Freelancer.rehydrateFreelancer(freelancerId, freelancerRepository.getById(freelancerId))

        when (consultingType) {
            is ConsultingType.NutritionConsulting -> {
                if (freelancer.getPersonalData().role != FreelancerRole.Nutritionist()) {
                    return false
                }
            }
            is ConsultingType.AthleticTrainerConsulting -> {
                if (freelancer.getPersonalData().role != FreelancerRole.AthleticTrainer()) {
                    return false
                }
            }
            is ConsultingType.BiomechanicalConsulting -> {
                if (freelancer.getPersonalData().role != FreelancerRole.Biomechanical()) {
                    return false
                }
            }
            is ConsultingType.PhysiotherapyConsulting -> {
                if (freelancer.getPersonalData().role != FreelancerRole.Physiotherapist()) {
                    return false
                }
            }
        }

        return true
    }
}
