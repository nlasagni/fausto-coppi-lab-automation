package it.unibo.lss.fcla.consulting.ui

import it.unibo.lss.fcla.consulting.application.controllers.IController
import it.unibo.lss.fcla.consulting.application.presentation.IRequest
import it.unibo.lss.fcla.consulting.application.presentation.IResponse
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ExamineAllSummariesForMemberRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveAthleticTrainerConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveBiomechanicalConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceiveNutritionistConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.ReceivePhysiotherapyConsultingRequest
import it.unibo.lss.fcla.consulting.application.presentation.consulting.UpdateConsultingSummaryRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateAthleticTrainerFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateBiomechanicalFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateNutritionistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreatePhysiotherapistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.DeleteFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.UpdateFreelancerAvailabilityForDayRequest
import it.unibo.lss.fcla.consulting.ui.MenuUtils.Companion.parseDateFromInput
import it.unibo.lss.fcla.consulting.ui.MenuUtils.Companion.parseTimeFromInput
import it.unibo.lss.fcla.consulting.ui.MenuUtils.Companion.readFromConsole

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
     * Helper enum representing a type of consulting
     */
    private enum class ConsultingType {
        NutritionistConsulting, PhysiotherapyConsulting, AthleticTrainerConsulting, BiomechanicalConsulting
    }

    /**
     * Helper enum representing a type of freelancer
     */
    private enum class FreelancerType {
        NutritionistFreelancer, PhysiotherapyFreelancer, AthleticTrainerFreelancer, BiomechanicalFreelancer
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
        when (readLine()) {
            "1" -> freelancerController.execute(
                packFreelancerRequest(FreelancerType.AthleticTrainerFreelancer)
            )
            "2" -> freelancerController.execute(
                packFreelancerRequest(FreelancerType.PhysiotherapyFreelancer)
            )
            "3" -> freelancerController.execute(
                packFreelancerRequest(FreelancerType.NutritionistFreelancer)
            )
            "4" -> freelancerController.execute(
                packFreelancerRequest(FreelancerType.BiomechanicalFreelancer)
            )
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

        when (val choice = readLine()) {
            "1", "2", "3" -> {
                val id = readFromConsole("Insert a valid freelancer ID to manages")

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
            "4" -> currentMenu = NestingMenu.FreelancerMenu
        }
    }

    /**
     * Read the input of the user and setup the requests to send to the controller
     */
    private fun readConsultingSubmenu() {

        when (readLine()) {
            "1" -> {
                consultingController.execute(
                    packConsultingRequest(ConsultingType.AthleticTrainerConsulting)
                )
            }
            "2" -> {
                consultingController.execute(
                    packConsultingRequest(ConsultingType.PhysiotherapyConsulting)
                )
            }
            "3" -> {
                consultingController.execute(
                    packConsultingRequest(ConsultingType.NutritionistConsulting)
                )
            }
            "4" -> {
                consultingController.execute(
                    packConsultingRequest(ConsultingType.BiomechanicalConsulting)
                )
            }
            "5" -> {
                val id = readFromConsole("Insert a valid consulting id")
                val desc = readFromConsole("Insert the new description for the summary")
                consultingController.execute(
                    UpdateConsultingSummaryRequest(consultingId = id, description = desc)
                )
            }
            "6" -> {
                val id = readFromConsole("Insert a valid member id")
                consultingController.execute(ExamineAllSummariesForMemberRequest(memberId = id))
            }
            "7" -> currentMenu = NestingMenu.MainMenu
        }
    }

    /**
     *
     */
    private fun packFreelancerRequest(type: FreelancerType): IRequest {

        val freelancerId = MenuUtils.readFromConsole("Insert a valid freelancer id")
        val firstName = MenuUtils.readFromConsole("Insert a valid firstName")
        val lastName = MenuUtils.readFromConsole("Insert a valid lastName")

        return when (type) {
            FreelancerType.AthleticTrainerFreelancer -> {
                CreateAthleticTrainerFreelancerRequest(freelancerId, firstName, lastName)
            }
            FreelancerType.BiomechanicalFreelancer -> {
                CreateBiomechanicalFreelancerRequest(freelancerId, firstName, lastName)
            }
            FreelancerType.NutritionistFreelancer -> {
                CreateNutritionistFreelancerRequest(freelancerId, firstName, lastName)
            }
            FreelancerType.PhysiotherapyFreelancer -> {
                CreatePhysiotherapistFreelancerRequest(freelancerId, firstName, lastName)
            }
        }
    }

    /**
     *
     */
    private fun packConsultingRequest(type: ConsultingType): IRequest {
        val consultingId = (++nextConsultingId).toString()
        val freelancerId = readFromConsole("Insert a valid freelancer id")
        val memberId = readFromConsole("Insert a valid member id")
        val consultingDate = parseDateFromInput("Consulting date")
        val description = readFromConsole("Insert the description")

        return when (type) {
            ConsultingType.AthleticTrainerConsulting -> {
                ReceiveAthleticTrainerConsultingRequest(
                    consultingId,
                    memberId,
                    consultingDate,
                    freelancerId,
                    description
                )
            }
            ConsultingType.NutritionistConsulting -> {
                ReceiveNutritionistConsultingRequest(consultingId, memberId, consultingDate, freelancerId, description)
            }
            ConsultingType.BiomechanicalConsulting -> {
                ReceiveBiomechanicalConsultingRequest(consultingId, memberId, consultingDate, freelancerId, description)
            }
            ConsultingType.PhysiotherapyConsulting -> {
                ReceivePhysiotherapyConsultingRequest(consultingId, memberId, consultingDate, freelancerId, description)
            }
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
