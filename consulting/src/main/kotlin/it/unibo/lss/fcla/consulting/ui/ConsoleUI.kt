package it.unibo.lss.fcla.consulting.ui

import com.sun.tools.javah.Util
import it.unibo.lss.fcla.consulting.application.controllers.BaseController
import it.unibo.lss.fcla.consulting.application.controllers.FreelancerController
import it.unibo.lss.fcla.consulting.application.presentation.IResponse
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateAthleticTrainerFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateBiomechanicalFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreateNutritionistFreelancerRequest
import it.unibo.lss.fcla.consulting.application.presentation.freelancer.CreatePhysiotherapistFreelancerRequest
import it.unibo.lss.fcla.consulting.domain.freelancer.FreelancerId
import java.lang.Integer.parseInt

/**
 * @author Stefano Braggion
 */
class ConsoleUI(
    private val freelancerController: BaseController
) : IView {

    private var currentMenu: NestingMenu = NestingMenu.MainMenu
    private var running = true

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
                "3) Exit application";
        println(menu)
    }

    /**
     *
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
                "6) Back to previous menu";
        println(submenu)
    }

    /**
     *
     */
    private fun readFreelancerSubmenu() {
        val choice = readLine()

        when {
            parseInt(choice) <= 4 -> {
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
            parseInt(choice) == 5 -> {
                currentMenu = NestingMenu.AvailabilityMenu
            }
            parseInt(choice) == 6 -> {
                currentMenu = NestingMenu.MainMenu
            }
        }

        when (readLine()) {
            "1" -> {



                println("executed creation")

                currentMenu = NestingMenu.FreelancerMenu
            }
            "5" -> currentMenu = NestingMenu.AvailabilityMenu
            "6" -> currentMenu = NestingMenu.MainMenu
        }
    }

    /**
     *
     */
    private fun plotAvailabilitiesSubmenu() {
        val submenu = "Select which operation to perform to manage freelancers availabilities: \n" +
                "1) Create new availability \n" +
                "2) Update an existing availability \n" +
                "3) Delete an existing availability \n" +
                "4) Back to previous menu";
        println(submenu)
    }

    /**
     *
     */
    private fun readAvailabilitiesSubmenu() {

        when (readLine()) {
            "1", "2", "3" -> println("OK")
            "4" -> currentMenu = NestingMenu.FreelancerMenu
        }
    }

    /**
     *
     */
    private fun plotConsultingSubmenu() {
        val submenu = "Select which operation to perform to manage consulting: \n" +
                "1) Create a new athletic trainer consulting \n" +
                "2) Create a new physiotherapist consulting \n" +
                "3) Create a new nutritionist consulting \n" +
                "4) Create a new biomechanical consulting \n" +
                "5) Update an existing consulting summary \n" +
                "6) Get all summaries for a member \n" +
                "7) Back to previous menu";
        println(submenu)
    }

    /**
     *
     */
    private fun readConsultingSubmenu(){
        when (readLine()) {
            "1", "2", "3", "4", "5", "6" -> println("OK")
            "7" -> currentMenu = NestingMenu.MainMenu
        }
    }

    /**
     * Main loop
     */
    fun startUI(){
        while(running) {
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

    override fun render(response: IResponse) {
        println(response.toString())
    }
}
