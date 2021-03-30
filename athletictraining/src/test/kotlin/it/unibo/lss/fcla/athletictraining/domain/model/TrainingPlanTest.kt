package it.unibo.lss.fcla.athletictraining.domain.model

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athletictraining.domain.exception.NameMustNotBeEmpty
import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutMustBeScheduledDuringPeriodOfTraining
import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutOnSameDateTimeAlreadyExists
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

/**
 * Test of the [TrainingPlan] domain Entity.
 *
 * @author Nicola Lasagni on 04/03/2021.
 */
class TrainingPlanTest : FreeSpec({
    lateinit var trainingPlanName: String
    lateinit var purpose: Purpose
    lateinit var periodOfTraining: PeriodOfTraining
    lateinit var validTrainingPlan: TrainingPlan

    /**
     * Setup before every test.
     */
    beforeAny {
        trainingPlanName = "Training Plan"
        purpose = Purpose.Strengthening()
        periodOfTraining = PeriodOfTraining(LocalDate.now(), LocalDate.now().plusWeeks(5))
        validTrainingPlan = TrainingPlan(
            trainingPlanName,
            purpose,
            periodOfTraining
        )
    }

    "TrainingPlan should" - {
        "have a name" - {
            assertThrows<NameMustNotBeEmpty> {
                TrainingPlan(
                    "",
                    purpose,
                    periodOfTraining
                )
            }
        }
        "offer a snapshot of itself" - {
            val snapshot = validTrainingPlan.snapshot()
            Assertions.assertEquals(trainingPlanName, snapshot.name)
            Assertions.assertEquals(purpose, snapshot.purpose)
            Assertions.assertEquals(periodOfTraining, snapshot.periodOfTraining)
        }
        "allow postponing the training period end" - {
            val postponedEnd = periodOfTraining.end.plusWeeks(1)
            assertDoesNotThrow {
                validTrainingPlan.postponeTrainingPeriodEnd(postponedEnd)
            }
            val snapshot = validTrainingPlan.snapshot()
            Assertions.assertEquals(postponedEnd, snapshot.periodOfTraining.end)
        }
        "allow scheduling of workouts" - {
            val workout = Workout("Workout", LocalDate.now(), LocalTime.now())
            assertDoesNotThrow { validTrainingPlan.scheduleWorkout(workout) }
        }
        "not allow scheduling of workout out of the period of training" - {
            val outOfPeriodWorkout = Workout(
                "Workout",
                LocalDate.now().minusDays(2),
                LocalTime.now()
            )
            assertThrows<WorkoutMustBeScheduledDuringPeriodOfTraining> {
                validTrainingPlan.scheduleWorkout(outOfPeriodWorkout)
            }
        }
        "not allow scheduling of workouts with same date and time" - {
            val workout = Workout(
                "Workout",
                LocalDate.now(),
                LocalTime.now()
            )
            validTrainingPlan.scheduleWorkout(workout)
            assertThrows<WorkoutOnSameDateTimeAlreadyExists> {
                validTrainingPlan.scheduleWorkout(workout)
            }
        }
    }
})
