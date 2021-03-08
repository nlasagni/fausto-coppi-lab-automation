package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.common.IRepository
import it.unibo.lss.fcla.consulting.domain.consulting.*
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

/**
 * @author Stefano Braggion
 *
 *
 */
class ConsultingUseCases(
    private val repository: IRepository<Consulting>
){

    /**
     * FCLAC-1 Examine consulting summaries
     */
    fun examineConsultingSummaries(consultingId: ConsultingId, memberId: MemberId) {
        val consulting = Consulting.rehydrateConsulting(consultingId, memberId, repository.getById(consultingId))
    }

    /**
     * FCLAC-7 Receive Consulting
     */
    fun receivePhysiotherapyConsulting(consultingId: ConsultingId, memberId: MemberId,
                                       consultingDate: Date, freelancerId: FreelancerId,
                                       description: String) {
        /**
         * create a new physiotherapy consulting
         */
        if(repository.getById(consultingId).count() > 0) {
            throw ConsultingShouldHaveAUniqueId()
        }
        val consulting = createConsultingOfType(consultingId, memberId, consultingDate, freelancerId,
        description, ConsultingType.PhysioterapyConsulting())

        repository.save(consulting)
    }

    /**
     * FCLAC-7 Receive Consulting
     */
    fun receiveNutritionistConsulting(consultingId: ConsultingId, memberId: MemberId,
                                      consultingDate: Date, freelancerId: FreelancerId,
                                      description: String) {
        /**
         * create a new nutritionist consulting
         */
        if(repository.getById(consultingId).count() > 0) {
            throw ConsultingShouldHaveAUniqueId()
        }
        val consulting = createConsultingOfType(consultingId, memberId, consultingDate, freelancerId,
            description, ConsultingType.NutritionConsulting())

        repository.save(consulting)
    }

    /**
     * FCLAC-7 Receive Consulting
     */
    fun receiveBiomechanicsConsulting(consultingId: ConsultingId, memberId: MemberId,
                                      consultingDate: Date, freelancerId: FreelancerId,
                                      description: String) {
        /**
         * create a new biomechanics consulting
         */
        if(repository.getById(consultingId).count() > 0) {
            throw ConsultingShouldHaveAUniqueId()
        }

        val consulting = createConsultingOfType(consultingId, memberId, consultingDate, freelancerId,
            description, ConsultingType.BiomechanicsConsulting())

        repository.save(consulting)
    }

    /**
     * FCLAC-7 Receive Consulting
     */
    fun receiveAthleticTrainerConsulting(consultingId: ConsultingId, memberId: MemberId,
                                         consultingDate: Date, freelancerId: FreelancerId,
                                         description: String) {
        /**
         * create a new athletic trainer consulting
         */
        if(repository.getById(consultingId).count() > 0) {
            throw ConsultingShouldHaveAUniqueId()
        }
        val consulting = Consulting.createConsulting(consultingId, memberId, consultingDate,
            freelancerId, ConsultingType.AthleticTrainerConsulting(), description)

        repository.save(consulting)
    }


    /**
     * FCLAC-8 Manage Consulting Summaries (Update)
     */
    fun updateConsultingSummary(consultingId: ConsultingId, memberId: MemberId, description: String) {
        /**
         * update the consulting with given id
         */
        if(repository.getById(consultingId).count() == 0) {
            throw ConsultingWithGivenIdDoesNotExist()
        }

        val consulting = Consulting.rehydrateConsulting(consultingId, memberId, repository.getById(consultingId))
        consulting.updateSummaryDescription(description)
        repository.save(consulting)
    }

    /**
     * FCLAC-8 Manage Consulting Summaries (Delete)
     */
    fun deleteConsultingSummary(consultingId: ConsultingId) {
        /**
         * delete the consulting with given id from the member
         */
    }

    /**
     * Utility method able to create a consulting
     */
    private fun createConsultingOfType(
        consultingId: ConsultingId, memberId: MemberId,
        consultingDate: Date, freelancerId: FreelancerId,
        description: String, type: ConsultingType
    ): Consulting {

        return Consulting.createConsulting(
            consultingId, memberId, consultingDate,
            freelancerId, type, description
        )
    }

    companion object {

        /**
         * FCLAC-9 Retrieve Profile
         */
        fun retrieveProfile() {

        }

        /**
         *
         */
        fun updateFreelancerAvailabilityForDay() {

        }

        /**
         *
         */
        fun createFreelancerAvailabilityForDay() {

        }

        /**
         *
         */
        fun deleteFreelancerAvailabilityForDay() {

        }

        /**
         *
         */
        fun getFreelancerAvailabilityForDay() {

        }
    }

}