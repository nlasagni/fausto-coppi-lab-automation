package it.unibo.lss.fcla.reservation.application

import it.unibo.lss.fcla.reservation.domain.usecases.CommandReservationUseCase
import it.unibo.lss.fcla.reservation.domain.usecases.EventStore
import it.unibo.lss.fcla.reservation.domain.usecases.QueryReservationUseCase
import it.unibo.lss.fcla.reservation.domain.usecases.RequestFailedException
import it.unibo.lss.fcla.reservation.domain.usecases.facades.ConsultingReservationDateFacade
import it.unibo.lss.fcla.reservation.domain.usecases.facades.WorkoutReservationDateFacade
import it.unibo.lss.fcla.reservation.persistence.RepositoryInMemory
import it.unibo.lss.fcla.reservation.ui.ConsoleUI
import java.util.UUID

/**
 * Controller class that handle application logic interacting with an UserInterface
 *
 * This class can be replaced with a similar one that use infrastructure instead of UI
 *
 */
class Controller {

    private val agendaId = UUID.randomUUID()
    private val ledgerId = UUID.randomUUID()
    private val memberId = UUID.randomUUID()
    private val firstName = "Luca"
    private val lastName = "Viola"
    private val noConsultingReservation = "No consulting reservation available"
    private val noWorkoutReservation = "No workout reservation available"

    private val repository = RepositoryInMemory()

    private val eventStore = EventStore(repository.readEvents())

    private val cmd = CommandReservationUseCase(agendaId, ledgerId, eventStore, repository)
    private val query = QueryReservationUseCase(agendaId, ledgerId, eventStore)

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

    private fun useWorkoutRes(toDo: (List<WorkoutReservationDateFacade>) -> Unit) {
        try {
            val resList = query.retrieveMemberWorkoutReservations(memberId)
            if (resList.isNotEmpty()) {
                toDo(resList)
            } else {
                ui.printMsg(noWorkoutReservation)
            }
        } catch (exception: RequestFailedException) {
            ui.printMsg(exception.message)
        }
    }

    private fun useConsultingRes(toDo: (List<ConsultingReservationDateFacade>) -> Unit) {
        try {
            val resList = query.retrieveMemberConsultingReservations(memberId)
            if (resList.isNotEmpty()) {
                toDo(resList)
            } else {
                ui.printMsg(noConsultingReservation)
            }
        } catch (exception: RequestFailedException) {
            ui.printMsg(exception.message)
        }
    }

    /**
     * start the ui waiting for user inputs
     */
    fun start() {
        var exit = false
        do {
            when (ui.readCommand()) {
                reqCreateConsRes -> {
                    ui.printMsg(
                        cmd.requestCreateConsultingReservation(ui.readF(), ui.readD(), firstName, lastName, memberId)
                    )
                }
                reqCreateWorkRes -> {
                    ui.printMsg(
                        cmd.requestCreateWorkoutReservation(ui.readA(), ui.readD(), firstName, lastName, memberId)
                    )
                }
                reqUpdateConsRes -> {
                    useConsultingRes { rL ->
                        ui.printMsg(
                            cmd.requestUpdateConsultingReservation(ui.chooseConsRes(rL), ui.readF(), ui.readD())
                        )
                    }
                }
                reqUpdateWorkRes -> {
                    useWorkoutRes { rL ->
                        ui.printMsg(
                            cmd.requestUpdateWorkoutReservation(ui.chooseWorkRes(rL), ui.readA(), ui.readD())
                        )
                    }
                }
                reqDeleteConsRes -> {
                    useConsultingRes { rL ->
                        ui.printMsg(
                            cmd.requestDeleteConsultingReservation(ui.chooseConsRes(rL), memberId)
                        )
                    }
                }
                reqDeleteWorkRes -> {
                    useWorkoutRes { rL ->
                        ui.printMsg(
                            cmd.requestDeleteWorkoutReservation(ui.chooseWorkRes(rL), memberId)
                        )
                    }
                }
                reqConsResDetails -> {
                    useConsultingRes { rL ->
                        ui.printConsResDetails(
                            query.retrieveConsultingReservation(ui.chooseConsRes(rL))
                        )
                    }
                }
                reqWorkResDetails -> {
                    useWorkoutRes { rL ->
                        ui.printWorkResDetails(query.retrieveWorkoutReservation(ui.chooseWorkRes(rL)))
                    }
                }
                reqExit -> exit = true
            }
        } while (! exit)
    }
}
