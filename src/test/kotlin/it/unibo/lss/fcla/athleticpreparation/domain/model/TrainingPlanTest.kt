package it.unibo.lss.fcla.athleticpreparation.domain.model

import io.kotest.core.spec.style.FreeSpec
import it.unibo.lss.fcla.athleticpreparation.domain.exception.NameMustNotBeEmpty
import it.unibo.lss.fcla.athleticpreparation.domain.exception.TrainingPlanMustBelongToAthleticPreparation
import it.unibo.lss.fcla.athleticpreparation.domain.exception.WorkoutMustBeScheduledDuringPeriodOfTraining
import it.unibo.lss.fcla.athleticpreparation.domain.exception.WorkoutOnSameDateTimeAlreadyExists
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Nicola Lasagni on 04/03/2021.
 */
class TrainingPlanTest : FreeSpec({
    lateinit var athleticPreparationId: String
    lateinit var trainingPlanName: String
    lateinit var purpose: Purpose
    lateinit var periodOfTraining: PeriodOfTraining
    lateinit var validTrainingPlan: TrainingPlan

    beforeAny {
        athleticPreparationId = "1234"
        trainingPlanName = "Training Plan"
        purpose = Purpose.Strengthening()
        periodOfTraining = PeriodOfTraining(LocalDate.now(), LocalDate.now().plusWeeks(5))
        validTrainingPlan = TrainingPlan(
                athleticPreparationId,
                trainingPlanName,
                purpose,
                periodOfTraining
        )
    }

    "TrainingPlan should" - {
        "belong to an AthleticPreparation" - {
            assertThrows<TrainingPlanMustBelongToAthleticPreparation> {
                TrainingPlan(
                        "",
                        trainingPlanName,
                        purpose,
                        periodOfTraining
                )
            }
        }
        "have a name" - {
            assertThrows<NameMustNotBeEmpty> {
                TrainingPlan(
                        athleticPreparationId,
                        "",
                        purpose,
                        periodOfTraining
                )
            }
        }
        "offer a snapshot of itself" - {
            val snapshot = validTrainingPlan.snapshot()
            Assertions.assertEquals(athleticPreparationId, snapshot.athleticPreparationId)
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
            val workout = Workout("1234", "Workout", LocalDate.now(), LocalTime.now())
            assertDoesNotThrow { validTrainingPlan.scheduleWorkout(workout) }
        }
        "not allow scheduling of workout out of the period of training" - {
            val outOfPeriodWorkout = Workout(
                    "1234",
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
                    "1234",
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
