/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.consulting.ui

import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Stefano Braggion
 *
 * Utility class for UI
 */
class MenuUtils private constructor() {

    companion object {

        /**
         * Write [message] to std output and read the response
         */
        fun readFromConsole(message: String): String {
            println(message)
            return readLine() ?: ""
        }

        /**
         * Utility method that take [Integer] as input and compose a [LocalDate]
         */
        fun parseDateFromInput(message: String): LocalDate {
            println(message)
            println("Insert the day")
            val day = readLine()
            println("Insert the month")
            val month = readLine()
            println("Insert the year")
            val year = readLine()

            return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day))
        }

        /**
         * Utility method that take [Integer] as input and compose a [LocalTime]
         */
        fun parseTimeFromInput(message: String): LocalTime {
            println(message)
            println("Insert hours")
            val hours = readLine()
            println("Insert minutes")
            val minutes = readLine()

            return LocalTime.of(Integer.parseInt(hours), Integer.parseInt(minutes))
        }
    }
}
