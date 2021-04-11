package it.unibo.lss.fcla.athletictraining.infrastructure.ui

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Utility class that contains common constants and methods for reading from standard input.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
object ConsoleUtil {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH.mm")

    /**
     * The available commands of the [ConsoleView].
     */
    object Commands {
        const val trainingListCommand = 1
        const val workoutListCommand = 2
        const val exerciseListCommand = 3
        const val planTrainingCommand = 4
        const val extendTrainingCommand = 5
        const val completeTrainingCommand = 6
        const val scheduleWorkoutCommand = 7
        const val rescheduleWorkoutCommand = 8
        const val cancelScheduleCommand = 9
        const val buildWorkoutCommand = 10
        const val chooseExerciseCommand = 11
        const val removeExerciseCommand = 12
        const val createExerciseCommand = 13
        const val deleteExerciseCommand = 14
    }

    /**
     * Displays the specified [message] and reads a [String] from standard input.
     */
    fun requestStringField(message: String): String {
        print("$message: ")
        return readLine() ?: ""
    }

    /**
     * Displays the specified [message] and reads an [Int] from standard input.
     * If the input cannot be parsed, a [ViewValidationException] is thrown.
     */
    fun requestIntField(message: String): Int {
        print("$message: ")
        return try {
            (readLine() ?: "").toInt()
        } catch (nfe: NumberFormatException) {
            throw ViewValidationException("Invalid number.")
        }
    }

    /**
     * Displays the specified [message] and reads a [LocalDate] from standard input.
     * The date must have format 'dd-MM-yyyy'.
     * If the input cannot be parsed, a [ViewValidationException] is thrown.
     */
    fun requestDateField(message: String): LocalDate {
        try {
            print("$message (dd-MM-yyyy): ")
            val dateString = readLine()
            if (dateString == null) {
                throw ViewValidationException("Invalid date.")
            } else {
                return LocalDate.parse(dateString, dateFormatter)
            }
        } catch (ex: DateTimeParseException) {
            throw ViewValidationException("Invalid date.")
        }
    }

    /**
     * Displays the specified [message] and reads a [LocalTime] from standard input.
     * The date must have format 'HH.mm'.
     * If the input cannot be parsed, a [ViewValidationException] is thrown.
     */
    fun requestTimeField(message: String): LocalTime {
        try {
            print("$message (HH.mm): ")
            val timeString = readLine()
            if (timeString == null) {
                throw ViewValidationException("Invalid time.")
            } else {
                return LocalTime.parse(timeString, timeFormatter)
            }
        } catch (ex: DateTimeParseException) {
            throw ViewValidationException("Invalid time.")
        }
    }
}
