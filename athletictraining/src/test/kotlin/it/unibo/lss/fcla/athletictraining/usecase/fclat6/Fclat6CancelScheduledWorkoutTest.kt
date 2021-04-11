package it.unibo.lss.fcla.athletictraining.usecase.fclat6

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryExerciseRepository
import it.unibo.lss.fcla.athletictraining.adapter.repository.InMemoryWorkoutRepository
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Configuration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Distance
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Duration
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Exercise
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.ExerciseId
import it.unibo.lss.fcla.athletictraining.domain.model.exercise.Intensity
import it.unibo.lss.fcla.athletictraining.domain.model.gymmachine.GymMachineId
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.Workout
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.service.WorkoutTotalDurationCalculatorImpl
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule
import it.unibo.lss.fcla.athletictraining.infrastructure.service.MemberProfileUpdaterImpl
import it.unibo.lss.fcla.athletictraining.usecase.DummyCancelScheduledWorkoutPresenter
import java.time.LocalDate
import java.time.LocalTime

/**
 * Test of the [Fclat6CancelScheduledWorkout] use case.
 *
 * @author Nicola Lasagni on 11/04/2021.
 */
class Fclat6CancelScheduledWorkoutTest : FreeSpec({

    val activeAthleticTrainingRepository = InMemoryActiveAthleticTrainingRepository()
    val workoutRepository = InMemoryWorkoutRepository()
    val exerciseRepository = InMemoryExerciseRepository()
    val memberProfileUpdater = MemberProfileUpdaterImpl()
    val workoutTotalDurationCalculator = WorkoutTotalDurationCalculatorImpl()
    val useCase = Fclat6CancelScheduledWorkout(
        DummyCancelScheduledWorkoutPresenter(),
        memberProfileUpdater,
        workoutTotalDurationCalculator,
        activeAthleticTrainingRepository,
        workoutRepository,
        exerciseRepository
    )

    val activeAthleticTrainingId = ActiveAthleticTrainingId("1234")
    val athleticTrainer = AthleticTrainerId("1234")
    val member = MemberId("1234")
    val beginning = LocalDate.now()
    val end = beginning.plusMonths(1)
    val period = Period(beginning, end)
    val purpose = Purpose("Athletic Preparation")
    val activeAthleticTraining = ActiveAthleticTraining(
        activeAthleticTrainingId,
        athleticTrainer,
        member,
        purpose,
        period
    )
    val workoutId = WorkoutId("1234")
    val workoutName = "Workout"
    val workout = Workout(workoutId, workoutName)
    val exerciseId = ExerciseId("1234")
    val name = "Exercise 1"
    val durationOfExecution = Duration.ofHours(1)
    val durationOfRest = Duration.ofMinutes(30)
    val intensity = Intensity(Intensity.NORMAL)
    val distance = Distance(1000)
    val configuration = Configuration(
        GymMachineId("1234"),
        intensity,
        distance
    )
    val exercise = Exercise(
        exerciseId,
        name,
        configuration,
        durationOfExecution,
        durationOfRest
    )
    val tenOClock = LocalTime.of(10, 0)

    "The use case FCLAT-6 should" - {
        "allow the cancellation of a workout scheduling" - {
            workout.prepareExercise(exerciseId)
            workout.prepareExercise(exerciseId)
            val schedule = Schedule(
                beginning,
                tenOClock,
                tenOClock.plusSeconds(
                    (durationOfExecution.seconds + durationOfRest.seconds) * 2
                )
            )
            activeAthleticTraining.scheduleWorkout(workoutId, schedule)
            exerciseRepository.add(exercise.snapshot())
            workoutRepository.add(workout.snapshot())
            activeAthleticTrainingRepository.add(activeAthleticTraining.snapshot())
            val request = CancelScheduledWorkoutRequest(
                activeAthleticTrainingId.value,
                workoutId.value,
                schedule.day,
                schedule.startTime
            )
            useCase.execute(request)
            val savedAthleticTraining =
                activeAthleticTrainingRepository.findById(activeAthleticTrainingId)
            savedAthleticTraining?.scheduledWorkouts?.shouldBeEmpty()
        }
    }
})
