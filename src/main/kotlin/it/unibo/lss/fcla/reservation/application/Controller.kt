package it.unibo.lss.fcla.reservation.application

import it.unibo.lss.fcla.reservation.domain.usecases.CommandReservationUseCase
import it.unibo.lss.fcla.reservation.domain.usecases.EventStore
import it.unibo.lss.fcla.reservation.domain.usecases.QueryReservationUseCase
import it.unibo.lss.fcla.reservation.domain.usecases.RequestFailedException
import it.unibo.lss.fcla.reservation.persistence.RepositoryInMemory
import it.unibo.lss.fcla.reservation.ui.ConsoleUI
import java.util.UUID

class Controller{

    private val agendaId = UUID.randomUUID()
    private val ledgerId = UUID.randomUUID()

    private val repository = RepositoryInMemory()

    private val eventStore = EventStore(repository.readEvents())

    private val commandUseCase = CommandReservationUseCase(agendaId, ledgerId, eventStore)
    private val queryReservationUseCase = QueryReservationUseCase(agendaId, ledgerId, eventStore)

    private val ui = ConsoleUI()

    /**
     * start the ui waiting for user inputs
     */
    fun start() {
        val memberId = UUID.randomUUID()
        val memberFirstName = "Luca"
        val memberLastName = "Viola"
        val noConsultingReservation = "No consulting reservation available"
        val noWorkoutReservation = "No workout reservation available"
        var exit = false
        do {
            when(ui.readCommand()){
                1 -> {
                    ui.printMsg(commandUseCase.requestCreateConsultingReservation(
                            ui.readFreelancer(),
                            ui.readDate(),
                            memberFirstName,
                            memberLastName,
                            memberId
                    ))
                }
                2 -> {
                    ui.printMsg(commandUseCase.requestCreateWorkoutReservation(
                            ui.readAim(),
                            ui.readDate(),
                            memberFirstName,
                            memberLastName,
                            memberId
                    ))
                }
                3 -> {
                    try {
                        val resList = queryReservationUseCase.retrieveMemberConsultingReservations(memberId)
                        ui.printMsg(commandUseCase.requestUpdateConsultingReservation(
                                ui.chooseConsultingReservation(resList),
                                ui.readFreelancer(),
                                ui.readDate()
                        ))
                    } catch (exception: RequestFailedException) {
                        ui.printMsg(noConsultingReservation)
                    }
                }
                4 -> {
                    try {
                        val resList = queryReservationUseCase.retrieveMemberWorkoutReservations(memberId)
                        ui.printMsg(commandUseCase.requestUpdateWorkoutReservation(
                                ui.chooseWorkoutReservation(resList),
                                ui.readAim(),
                                ui.readDate()
                        ))
                    } catch (exception: RequestFailedException) {
                        ui.printMsg(noWorkoutReservation)
                    }
                }
                5 -> {
                    try {
                        val resList = queryReservationUseCase.retrieveMemberConsultingReservations(memberId)
                        ui.printMsg(commandUseCase.requestDeleteConsultingReservation(
                                ui.chooseConsultingReservation(resList),
                                memberId
                        ))
                    } catch (exception: RequestFailedException) {
                        ui.printMsg(noConsultingReservation)
                    }
                }
                6 -> {
                    try {
                        val resList = queryReservationUseCase.retrieveMemberWorkoutReservations(memberId)
                        ui.printMsg(commandUseCase.requestDeleteWorkoutReservation(
                                ui.chooseWorkoutReservation(resList),
                                memberId
                        ))
                    } catch (exception: RequestFailedException) {
                        ui.printMsg(noWorkoutReservation)
                    }
                }
                7 -> {
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
                8 -> {
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
                9 -> exit = true
            }
        } while (! exit)
    }
}
