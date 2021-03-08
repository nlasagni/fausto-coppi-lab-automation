package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.common.IRepository
import it.unibo.lss.fcla.consulting.domain.consulting.*
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId

/**
 * @author Stefano Braggion
 *
 *
 */
class ConsultingUseCases {

    companion object {

        /**
         * FCLAC-1 Examine consulting summaries
         */
        fun examineConsultingSummaries(consultingId: ConsultingId, memberId: MemberId,
                                       repository: IRepository<Consulting>) {
            val consulting = Consulting.rehydrateConsulting(consultingId, memberId, repository.getById(consultingId))
        }

        /**
         * FCLAC-7 Receive Consulting
         */
        fun receivePhysiotherapyConsulting(consultingId: ConsultingId, memberId: MemberId,
                                           consultingDate: Date, freelancerId: FreelancerId,
                                           description: String, repository: IRepository<Consulting>) {
            /**
             * create a new physiotherapy consulting
             */
            if(repository.getById(consultingId).count() > 0) {
                throw ConsultingShouldHaveAUniqueId()
            }
            val consulting = Consulting.createConsulting(consultingId, memberId, consultingDate,
            freelancerId, ConsultingType.PhysioterapyConsulting(), description)

            repository.save(consulting)
        }

        /**
         * FCLAC-7 Receive Consulting
         */
        fun receiveNutritionistConsulting(consultingId: ConsultingId, memberId: MemberId,
                                           consultingDate: Date, freelancerId: FreelancerId,
                                           description: String, repository: IRepository<Consulting>) {
            /**
             * create a new nutritionist consulting
             */
            if(repository.getById(consultingId).count() > 0) {
                throw ConsultingShouldHaveAUniqueId()
            }
            val consulting = Consulting.createConsulting(consultingId, memberId, consultingDate,
                freelancerId, ConsultingType.NutritionConsulting(), description)

            repository.save(consulting)
        }

        /**
         * FCLAC-7 Receive Consulting
         */
        fun receiveBiomechanicsConsulting(consultingId: ConsultingId, memberId: MemberId,
                                          consultingDate: Date, freelancerId: FreelancerId,
                                          description: String, repository: IRepository<Consulting>) {
            /**
             * create a new biomechanics consulting
             */
            if(repository.getById(consultingId).count() > 0) {
                throw ConsultingShouldHaveAUniqueId()
            }
            val consulting = Consulting.createConsulting(consultingId, memberId, consultingDate,
                freelancerId, ConsultingType.BiomechanicsConsulting(), description)

            repository.save(consulting)
        }

        /**
         * FCLAC-7 Receive Consulting
         */
        fun receiveAthleticTrainerConsulting(consultingId: ConsultingId, memberId: MemberId,
                                          consultingDate: Date, freelancerId: FreelancerId,
                                          description: String, repository: IRepository<Consulting>) {
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
        fun updateConsultingSummary() {
            /**
             * update the consulting with given id
             */
        }

        /**
         * FCLAC-8 Manage Consulting Summaries (Delete)
         */
        fun deleteConsultingSummary() {
            /**
             * delete the consulting with given id from the member
             */
        }

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