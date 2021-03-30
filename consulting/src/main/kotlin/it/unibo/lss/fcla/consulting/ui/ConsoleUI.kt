package it.unibo.lss.fcla.consulting.ui

import it.unibo.lss.fcla.consulting.application.controllers.IController
import it.unibo.lss.fcla.consulting.application.presentation.IResponse
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveAthleticTrainerConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveBiomechanicalConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveNutritionistConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceivePhysiotherapyConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateAthleticTrainerFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateBiomechanicalFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateNutritionistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreatePhysiotherapistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.DeleteFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.UpdateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import it.unibo.lss.fcla.consulting.ui.MenuUtils.Companion.parseDateFromInput
import it.unibo.lss.fcla.consulting.ui.MenuUtils.Companion.parseTimeFromInput

/**
 * @author Stefano Braggion
 *
 * This is a basic UI, a concrete implementation of a [IView].
 */
class ConsoleUI(
    private val freelancerController: IController,
    private val consultingController: IController
) : IView {

    private var currentMenu: NestingMenu = NestingMenu.MainMenu
    private var running = true

    private var nextConsultingId = 0

    /**
     * Enum representing the current menu displayed
     */
    private enum class NestingMenu {
        MainMenu, ConsultingMenu, FreelancerMenu, AvailabilityMenu
    }

    /**
     * Plot the main menu of the application
     */
    private fun plotMainMenu() {
        val menu = "Select which operation to perform: \n" +
            "1) Manage consulting \n" +
            "2) Manage freelancers \n" +
            "3) Exit application"
        println(menu)
    }

    /**
     * Read the input of the user and change the showing menu
     */
    private fun readMainMenu() {

        when (readLine()) {
            "1" -> currentMenu = NestingMenu.ConsultingMenu
            "2" -> currentMenu = NestingMenu.FreelancerMenu
            "3" -> {
                println("Bye")
                running = false
            }
        }
    }

    /**
     * Read the input of the user and setup the requests to send to the controller
     */
    private fun readFreelancerSubmenu() {
        when (val choice = readLine()) {
            "1", "2", "3", "4" -> {
                val id = MenuUtils.readFromConsole("Insert a valid freelancer id")
                val fName = MenuUtils.readFromConsole("Insert a valid firstName")
                val lName = MenuUtils.readFromConsole("Insert a valid lastName")

                when (choice) {
                    "1" -> freelancerController.execute(
                        CreateAthleticTrainerFreelancerRequest(
                            freelancerId = id,
                            firstName = fName,
                            lastName = lName
                        )
                    )
                    "2" -> freelancerController.execute(
                        CreatePhysiotherapistFreelancerRequest(
                            freelancerId = id,
                            firstName = fName,
                            lastName = lName
                        )
                    )
                    "3" -> freelancerController.execute(
                        CreateNutritionistFreelancerRequest(
                            freelancerId = id,
                            firstName = fName,
                            lastName = lName
                        )
                    )
                    "4" -> freelancerController.execute(
                        CreateBiomechanicalFreelancerRequest(
                            freelancerId = id,
                            firstName = fName,
                            lastName = lName
                        )
                    )
                }
            }
            "5" -> {
                currentMenu = NestingMenu.AvailabilityMenu
            }
            "6" -> {
                currentMenu = NestingMenu.MainMenu
            }
        }
    }

    /**
     * Read the input of the user and setup the requests to send to the controller
     */
    private fun readAvailabilitiesSubmenu() {
        val choice = readLine()

        when {
            choice == "1" || choice == "2" || choice == "3" -> {
                println("Insert a valid freelancer ID to manages")
                val id = readLine() as FreelancerId

                when (choice) {
                    "1" -> {
                        val date = parseDateFromInput("Availability date")
                        val from = parseTimeFromInput("Starting time")
                        val to = parseTimeFromInput("End time")
                        freelancerController.execute(
                            CreateFreelancerAvailabilityForDayRequest(
                                freelancerId = id,
                                availabilityDate = date,
                                fromTime = from,
                                toTime = to
                            )
                        )
                    }
                    "2" -> {
                        val date = parseDateFromInput("Availability date")
                        val from = parseTimeFromInput("Starting time")
                        val to = parseTimeFromInput("End time")
                        freelancerController.execute(
                            UpdateFreelancerAvailabilityForDayRequest(
                                freelancerId = id,
                                availabilityDate = date,
                                fromTime = from,
                                toTime = to
                            )
                        )
                    }
                    "3" -> {
                        val date = parseDateFromInput("Availability date")
                        freelancerController.execute(
                            DeleteFreelancerAvailabilityForDayRequest(
                                freelancerId = id,
                                availabilityDate = date
                            )
                        )
                    }
                }
            }
            choice == "4" -> currentMenu = NestingMenu.FreelancerMenu
        }
    }

    /**
     * Read the input of the user and setup the requests to send to the controller
     */
    private fun readConsultingSubmenu() {

        when (val choice = readLine()) {
            "1", "2", "3", "4" -> {
                val id = (++nextConsultingId).toString()
                val fId = MenuUtils.readFromConsole("Insert a valid freelancer id")
                val mId = MenuUtils.readFromConsole("Insert a valid member id")
                val date = parseDateFromInput("Consulting date")
                val desc = MenuUtils.readFromConsole("Insert the description")

                when (choice) {
                    "1" -> {
                        consultingController.execute(
                            ReceiveAthleticTrainerConsultingRequest(
                                consultingId = id,
                                memberId = mId,
                                consultingDate = date,
                                freelancerId = fId,
                                description = desc
                            )
                        )
                    }
                    "2" -> {
                        consultingController.execute(
                            ReceivePhysiotherapyConsultingRequest(
                                consultingId = id,
                                memberId = mId,
                                consultingDate = date,
                                freelancerId = fId,
                                description = desc
                            )
                        )
                    }
                    "3" -> {
                        consultingController.execute(
                            ReceiveNutritionistConsultingRequest(
                                consultingId = id,
                                memberId = mId,
                                consultingDate = date,
                                freelancerId = fId,
                                description = desc
                            )
                        )
                    }
                    "4" -> {
                        consultingController.execute(
                            ReceiveBiomechanicalConsultingRequest(
                                consultingId = id,
                                memberId = mId,
                                consultingDate = date,
                                freelancerId = fId,
                                description = desc
                            )
                        )
                    }
                }
            }
            "5" -> currentMenu = NestingMenu.MainMenu
        }
    }

    /**
     * Main loop
     */
    fun startUI() {
        while (running) {
            when (currentMenu) {
                NestingMenu.MainMenu -> {
                    plotMainMenu()
                    readMainMenu()
                }
                NestingMenu.FreelancerMenu -> {
                    FreelancerMenu.plotFreelancerSubmenu()
                    readFreelancerSubmenu()
                }
                NestingMenu.ConsultingMenu -> {
                    ConsultingMenu.plotConsultingSubmenu()
                    readConsultingSubmenu()
                }
                NestingMenu.AvailabilityMenu -> {
                    FreelancerMenu.plotAvailabilitiesSubmenu()
                    readAvailabilitiesSubmenu()
                }
            }
        }
    }

    /**
     * Method that render the [response] of the operation requested
     */
    override fun render(response: IResponse) {
        println(response.toString())
    }
}
