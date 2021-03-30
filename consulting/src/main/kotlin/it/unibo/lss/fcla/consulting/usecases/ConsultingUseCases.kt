package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.common.AggregateId
import it.unibo.lss.fcla.consulting.common.IRepository
import it.unibo.lss.fcla.consulting.domain.consulting.Consulting
import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.consulting.MemberId
import it.unibo.lss.fcla.consulting.domain.consulting.createAthleticTrainerConsulting
import it.unibo.lss.fcla.consulting.domain.consulting.createBiomechanicalConsulting
import it.unibo.lss.fcla.consulting.domain.consulting.createNutritionistConsulting
import it.unibo.lss.fcla.consulting.domain.consulting.createPhysiotherapyConsulting
import it.unibo.lss.fcla.consulting.domain.contracts.DomainEvent
import it.unibo.lss.fcla.consulting.domain.freelancer.Freelancer
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.usecases.facades.ConsultingFacade
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
     * FCLAC-1 Examine consulting summaries
     */
    fun examineConsultingSummaries(consultingId: ConsultingId): Consulting {
        return Consulting.rehydrateConsulting(consultingId, repository.getById(consultingId))
    }

    /**
     * FCLAC-7 Receive Consulting
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
     * FCLAC-7 Receive Consulting
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
     * FCLAC-7 Receive Consulting
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
     * FCLAC-7 Receive Consulting
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
     * FCLAC-8 Manage Consulting Summaries (Update)
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
     * FCLAC-9 Retrieve all the summaries for a member
     */
    fun retrieveProfile(memberId: MemberId): List<Consulting> {
        val events: Map<AggregateId, List<DomainEvent>> = repository.getAllEvents()
        val entityList = mutableListOf<Consulting>()

        events.forEach {
            entityList += Consulting.rehydrateConsulting(it.key, it.value.toList())
        }

        println(entityList.filter { it.getMemberId() == memberId })

        return entityList.filter { it.getMemberId() == memberId }
    }

    /**
     * Utility method that check if the given [freelancerId] exist
     */
    private fun freelancerExist(freelancerId: FreelancerId) = freelancerRepository.getById(freelancerId).count() > 0
}
