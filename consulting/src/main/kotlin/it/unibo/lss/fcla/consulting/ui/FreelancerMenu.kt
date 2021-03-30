package it.unibo.lss.fcla.consulting.ui

/**
 * @author Stefano Braggion
 * Utility class that prints in the console the menu
 */
sealed class FreelancerMenu {

    companion object {
        /**
         * Plot the submenu related to the management of freelancers
         */
        fun plotFreelancerSubmenu() {
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
         * Plot the submenu related to the management of freelancers availabilities
         */
        fun plotAvailabilitiesSubmenu() {
            val submenu = "Select which operation to perform to manage freelancers availabilities: \n" +
                "1) Create new availability \n" +
                "2) Update an existing availability \n" +
                "3) Delete an existing availability \n" +
                "4) Back to previous menu"
            println(submenu)
        }
    }
}
