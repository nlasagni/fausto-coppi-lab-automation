/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.ui

/**
 * @author Stefano Braggion
 *
 * Utility class that prints in the console the menu
 */
sealed class ConsultingMenu {

    companion object {

        /**
         * Plot the submenu related to the management of consulting
         */
        fun plotConsultingSubmenu() {
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
    }
}
