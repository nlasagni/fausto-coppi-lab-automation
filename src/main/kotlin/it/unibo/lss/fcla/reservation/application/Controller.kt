package it.unibo.lss.fcla.reservation.application

import it.unibo.lss.fcla.reservation.domain.usecases.CommandReservationUseCase
import it.unibo.lss.fcla.reservation.domain.usecases.EventStore
import it.unibo.lss.fcla.reservation.domain.usecases.QueryReservationUseCase
import it.unibo.lss.fcla.reservation.domain.usecases.RequestFailedException
import it.unibo.lss.fcla.reservation.persistence.RepositoryInMemory
import it.unibo.lss.fcla.reservation.ui.ConsoleUI
import java.util.UUID

class Controller {

    private val agendaId = UUID.randomUUID()
    private val ledgerId = UUID.randomUUID()
    private val memberId = UUID.randomUUID()
    private val memberFirstName = "Luca"
    private val memberLastName = "Viola"
    private val noConsultingReservation = "No consulting reservation available"
    private val noWorkoutReservation = "No workout reservation available"

    private val repository = RepositoryInMemory()

    private val eventStore = EventStore(repository.readEvents())

    private val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore, repository)
    private val queryReservationUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)

    private val ui = ConsoleUI()

    companion object {
        private const val reqCreateConsRes = 1
        private const val reqCreateWorkRes = 2
        private const val reqUpdateConsRes = 3
        private const val reqUpdateWorkRes = 4
        private const val reqDeleteConsRes = 5
        private const val reqDeleteWorkRes = 6
        private const val reqConsResDetails = 7
        private const val reqWorkResDetails = 8
        private const val reqExit = 9
    }

    private fun createConsulting() {
        ui.printMsg(
            commandUseCase.requestCreateConsultingReservation(
                ui.readFreelancer(),
                ui.readDate(),
                memberFirstName,
                memberLastName,
                memberId
            )
        )
    }

    private fun createWorkout() {
        ui.printMsg(
            commandUseCase.requestCreateWorkoutReservation(
                ui.readAim(),
                ui.readDate(),
                memberFirstName,
                memberLastName,
                memberId
            )
        )
    }

    private fun updateConsulting() {
        try {
            val resList = queryReservationUseCase.retrieveMemberConsultingReservations(memberId)
            ui.printMsg(
                commandUseCase.requestUpdateConsultingReservation(
                    ui.chooseConsultingReservation(resList),
                    ui.readFreelancer(),
                    ui.readDate()
                )
            )
        } catch (exception: RequestFailedException) {
            ui.printMsg(noConsultingReservation)
        }
    }

    private fun updateWorkout() {
        try {
            val resList = queryReservationUseCase.retrieveMemberWorkoutReservations(memberId)
            ui.printMsg(
                commandUseCase.requestUpdateWorkoutReservation(
                    ui.chooseWorkoutReservation(resList),
                    ui.readAim(),
                    ui.readDate()
                )
            )
        } catch (exception: RequestFailedException) {
            ui.printMsg(noWorkoutReservation)
        }
    }

    private fun deleteConsulting() {
        try {
            val resList = queryReservationUseCase.retrieveMemberConsultingReservations(memberId)
            ui.printMsg(
                commandUseCase.requestDeleteConsultingReservation(
                    ui.chooseConsultingReservation(resList),
                    memberId
                )
            )
        } catch (exception: RequestFailedException) {
            ui.printMsg(noConsultingReservation)
        }
    }

    private fun deleteWorkout() {
        try {
            val resList = queryReservationUseCase.retrieveMemberWorkoutReservations(memberId)
            ui.printMsg(
                commandUseCase.requestDeleteWorkoutReservation(
                    ui.chooseWorkoutReservation(resList),
                    memberId
                )
            )
        } catch (exception: RequestFailedException) {
            ui.printMsg(noWorkoutReservation)
        }
    }

    private fun consultingDetails() {
        try {
            val resList = queryReservationUseCase.retrieveMemberConsultingReservations(memberId)
            ui.printConsultingReservationDetails(
                queryReservationUseCase
                    .retrieveConsultingReservation(ui.chooseConsultingReservation(resList))
            )
        } catch (exception: RequestFailedException) {
            ui.printMsg(noConsultingReservation)
        }
    }

    private fun workoutDetails() {
        try {
            val resList = queryReservationUseCase.retrieveMemberWorkoutReservations(memberId)
            ui.printWorkoutReservationDetails(
                queryReservationUseCase
                    .retrieveWorkoutReservation(ui.chooseWorkoutReservation(resList))
            )
        } catch (exception: RequestFailedException) {
            ui.printMsg(noWorkoutReservation)
        }
    }

    /**
     * start the ui waiting for user inputs
     */
    fun start() {
        var exit = false
        do {
            when (ui.readCommand()) {
                reqCreateConsRes -> createConsulting()
                reqCreateWorkRes -> createWorkout()
                reqUpdateConsRes -> updateConsulting()
                reqUpdateWorkRes -> updateWorkout()
                reqDeleteConsRes -> deleteConsulting()
                reqDeleteWorkRes -> deleteWorkout()
                reqConsResDetails -> consultingDetails()
                reqWorkResDetails -> workoutDetails()
                reqExit -> exit = true
            }
        } while (! exit)
    }
}
