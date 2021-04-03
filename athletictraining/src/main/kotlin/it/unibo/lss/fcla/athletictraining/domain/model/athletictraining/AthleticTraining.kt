package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingAlreadyCompleted
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveMember
import it.unibo.lss.fcla.athletictraining.domain.exception.PeriodExtensionCannotEndBeforeCurrentPeriod
import it.unibo.lss.fcla.athletictraining.domain.exception.PostponedPeriodMustHaveSameBeginningOfCurrentPeriod
import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutMustBeScheduledDuringPeriodOfTraining
import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutScheduleMustNotOverlap
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import java.time.LocalDateTime

/**
 * This is one of the main entities of the Athletic Training Bounded Context.
 *
 * An AthleticTraining must be prepared by an athletic trainer and for a member.
 *
 * The purpose of an AthleticTraining is to organize the training plans
 * which will bring the member to reach his/her athletic objectives.
 *
 * During a member's AthleticTraining it is not possible to have two TrainingPlan
 * in the same PeriodOfTraining.
 *
 * The lifecycle of an AthleticTraining ends when the athletic trainer
 * decides that it is completed.
 *
 * @property athleticTrainerId The id reference of the athletic trainer who is preparing the athletic training.
 * @property memberId The id reference of the member for whom the athletic training is being prepared.
 * @property period The period of athletic traning. See [Period].
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class AthleticTraining(
    private val athleticTrainerId: AthleticTrainerId,
    private val memberId: MemberId,
    private val purpose: Purpose,
    private var period: Period
) {

    private enum class Status {
        ACTIVE, COMPLETED
    }

    private val id: AthleticTrainingId
    private var scheduledWorkouts: List<ScheduledWorkout> = emptyList()
    private var status: Status = Status.ACTIVE

    init {
        if (athleticTrainerId.value.isEmpty()) {
            throw AthleticTrainingMustHaveAthleticTrainer()
        }
        if (memberId.value.isEmpty()) {
            throw AthleticTrainingMustHaveMember()
        }
        id = generateId()
    }

    /**
     * Returns a unique id of this AthleticTraining which will be stored
     * into the [id] private property.
     */
    private fun generateId(): AthleticTrainingId =
        AthleticTrainingId("$athleticTrainerId-$memberId-${period.beginning.dayOfYear}")

    /**
     * Postpones the [period] of this athletic training.
     * If the [postponedPeriod] doesn't begin the same day as the current [period],
     * throws a [PostponedPeriodMustHaveSameBeginningOfCurrentPeriod].
     * If the [postponedPeriod] ends before the current [period],
     * throws a [PeriodExtensionCannotEndBeforeCurrentPeriod].
     */
    fun postponeTrainingPeriodEnd(postponedPeriod: Period) {
        if (!postponedPeriod.hasSameBeginning(period)) {
            throw PostponedPeriodMustHaveSameBeginningOfCurrentPeriod()
        }
        if (!postponedPeriod.endsAfter(period)) {
            throw PeriodExtensionCannotEndBeforeCurrentPeriod()
        }
        period = postponedPeriod
    }

    /**
     * Schedules a workout for this AthleticTraining.
     * The workout must not be out of the [period], otherwise
     * a [WorkoutMustBeScheduledDuringPeriodOfTraining] exception will be thrown.
     * If another workout in the same date and time already exists, a
     * [WorkoutScheduleMustNotOverlap] exception will be thrown.
     */
    fun scheduleWorkout(workoutId: WorkoutId, schedule: Schedule) {
        if (isAlreadyCompleted()) {
            throw AthleticTrainingAlreadyCompleted()
        }
        if (isScheduleOutOfPeriod(schedule)) {
            throw WorkoutMustBeScheduledDuringPeriodOfTraining()
        }
        val desiredScheduledWorkout = ScheduledWorkout(workoutId, schedule)
        if (scheduleOverlaps(desiredScheduledWorkout)) {
            throw WorkoutScheduleMustNotOverlap()
        }
        scheduledWorkouts = scheduledWorkouts + desiredScheduledWorkout
    }

    /**
     * Checks if the desired [Schedule] of a workout is out of the
     * [period].
     */
    private fun isScheduleOutOfPeriod(schedule: Schedule): Boolean {
        val scheduleEndDateTime = LocalDateTime.of(schedule.day, schedule.endTime)
        return scheduleEndDateTime.isAfter(period.end)
    }

    /**
     * Checks if the desired [Schedule] overlaps with an existing one.
     */
    private fun scheduleOverlaps(scheduledWorkout: ScheduledWorkout): Boolean {
        return scheduledWorkouts.any {
            it.overlapsWith(scheduledWorkout)
        }
    }

    /**
     * Completes this AthleticTraining.
     */
    fun complete() {
        status = Status.COMPLETED
    }

    /**
     * Checks if this AthleticTraining is completed.
     */
    private fun isAlreadyCompleted() = status == Status.COMPLETED

    /**
     * Generates an [AthleticTrainingSnapshot] with the information about this AthleticTraining.
     */
    fun snapshot() = AthleticTrainingSnapshot(
        id,
        athleticTrainerId,
        memberId,
        purpose,
        period,
        scheduledWorkouts
    )
}
