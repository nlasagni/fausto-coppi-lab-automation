package it.unibo.lss.fcla.consulting.usecases

import it.unibo.lss.fcla.consulting.domain.consulting.ConsultingId
import it.unibo.lss.fcla.consulting.domain.member.MemberId

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
        fun examineConsultingSummaries(memberId: MemberId) {
            /**
             * Retrieve all consulting summaries from the member
             */
        }

        /**
         * FCLAC-7 Receive Consulting
         */
        fun receiveConsulting() {
            /**
             * create a new consulting
             * push the consulting to the member aggregate
             */
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
    }

}