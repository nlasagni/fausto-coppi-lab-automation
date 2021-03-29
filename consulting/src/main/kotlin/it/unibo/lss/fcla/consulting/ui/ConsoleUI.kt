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
import it.unibo.lss.fcla.consulting.domain.consulting.MemberId
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import java.lang.Integer.parseInt
import java.time.LocalDate
import java.time.LocalTime

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
     * Plot the submenu related to the management of freelancers
     */
    private fun plotFreelancerSubmenu() {
        val submenu = "Select which operation to perform in the freelancer management area: \n" +
            "1) Create a new Athletic Trainer freelancer \n" +
            "2) Create a new Physiotherapist freelancer \n" +
            "3) Create a new Nutritionist freelancer \n" +
            "4) Create a new Biomechanical freelancer \n" +
            "5) Manage freelancers availabilities \n" +
            "6) Back to previous menu"
        println(submenu)
    }

    /**
     * Read the input of the user and setup the requests to send to the controller
     */
    private fun readFreelancerSubmenu() {
        val choice = readLine()

        when {
            choice == "1" || choice == "2" || choice == "3" || choice == "4" -> {
                println("Insert a valid freelancer ID")
                val id = readLine() as FreelancerId
                println("Insert a valid firstName")
                val firstName = readLine()
                println("Insert a valid lastName")
                val lastName = readLine()

                when (choice) {
                    "1" -> freelancerController.execute(
                        CreateAthleticTrainerFreelancerRequest(
                            freelancerId = id,
                            firstName ?: "",
                            lastName ?: ""
                        )
                    )
                    "2" -> freelancerController.execute(
                        CreatePhysiotherapistFreelancerRequest(
                            freelancerId = id,
                            firstName ?: "",
                            lastName ?: ""
                        )
                    )
                    "3" -> freelancerController.execute(
                        CreateNutritionistFreelancerRequest(
                            freelancerId = id,
                            firstName ?: "",
                            lastName ?: ""
                        )
                    )
                    "4" -> freelancerController.execute(
                        CreateBiomechanicalFreelancerRequest(
                            freelancerId = id,
                            firstName ?: "",
                            lastName ?: ""
                        )
                    )
                }
            }
            choice == "5" -> {
                currentMenu = NestingMenu.AvailabilityMenu
            }
            choice == "6" -> {
                currentMenu = NestingMenu.MainMenu
            }
        }
    }

    /**
     * Plot the submenu related to the management of freelancers availabilities
     */
    private fun plotAvailabilitiesSubmenu() {
        val submenu = "Select which operation to perform to manage freelancers availabilities: \n" +
            "1) Create new availability \n" +
            "2) Update an existing availability \n" +
            "3) Delete an existing availability \n" +
            "4) Back to previous menu"
        println(submenu)
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
     * Plot the submenu related to the management of consulting
     */
    private fun plotConsultingSubmenu() {
        val submenu = "Select which operation to perform to manage consulting: \n" +
            "1) Create a new athletic trainer consulting \n" +
            "2) Create a new physiotherapist consulting \n" +
            "3) Create a new nutritionist consulting \n" +
            "4) Create a new biomechanical consulting \n" +
            "5) Update an existing consulting summary \n" +
            "6) Get all summaries for a member \n" +
            "7) Back to previous menu"
        println(submenu)
    }

    /**
     * Read the input of the user and setup the requests to send to the controller
     */
    private fun readConsultingSubmenu() {

        val choice = readLine()

        when {
            choice == "1" || choice == "2" || choice == "3" || choice == "4" -> {

                val id = (++nextConsultingId).toString()
                println("Insert a valid freelancer id")
                val fId = readLine() as FreelancerId
                println("Insert a valid member id")
                val mId = readLine() as MemberId
                val date = parseDateFromInput("Consulting date")
                println("Insert the description")
                val desc = readLine() ?: ""

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
            choice == "5" -> currentMenu = NestingMenu.MainMenu
        }
    }

    /**
     * Utility method that take [Integer] as input and compose a [LocalDate]
     */
    private fun parseDateFromInput(message: String): LocalDate {
        println(message)
        println("Insert the day")
        val day = readLine()
        println("Insert the month")
        val month = readLine()
        println("Insert the year")
        val year = readLine()

        return LocalDate.of(parseInt(year), parseInt(month), parseInt(day))
    }

    /**
     * Utility method that take [Integer] as input and compose a [LocalTime]
     */
    private fun parseTimeFromInput(message: String): LocalTime {
        println(message)
        println("Insert hours")
        val hours = readLine()
        println("Insert minutes")
        val minutes = readLine()

        return LocalTime.of(parseInt(hours), parseInt(minutes))
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
                    plotFreelancerSubmenu()
                    readFreelancerSubmenu()
                }
                NestingMenu.ConsultingMenu -> {
                    plotConsultingSubmenu()
                    readConsultingSubmenu()
                }
                NestingMenu.AvailabilityMenu -> {
                    plotAvailabilitiesSubmenu()
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
