package it.unibo.lss.fcla.consulting.ui

import com.sun.tools.javah.Util
import it.unibo.lss.fcla.consulting.application.controllers.FreelancerController

/**
 * @author Stefano Braggion
 */
class ConsoleUI : IView {

    private val viewModel = ConsoleViewModel()
    private val freelancerController = FreelancerController()
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

        when (readLine()) {
            "1", "2", "3", "4" -> {
                println("OK")
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
                    println("NOT IMPLEMENTED")
                }
                NestingMenu.AvailabilityMenu -> {
                    plotAvailabilitiesSubmenu()
                    readAvailabilitiesSubmenu()
                }
            }
        }

    }
}
