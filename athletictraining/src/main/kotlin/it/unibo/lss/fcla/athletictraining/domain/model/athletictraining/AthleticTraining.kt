package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.AthleticTrainingAlreadyCompleted
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.AthleticTrainingMustHaveMember
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.PeriodExtensionCannotEndBeforeCurrentPeriod
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.PostponedPeriodMustHaveSameBeginningOfCurrentPeriod
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.ScheduledWorkoutNotFound
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.WorkoutMustBeScheduledDuringPeriodOfTraining
import it.unibo.lss.fcla.athletictraining.domain.model.athletictraining.exeption.WorkoutScheduleMustNotOverlap
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
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
     * Retrieves the [AthleticTrainerId] that made this AthleticTraining.
     * @return The [AthleticTrainerId] that made this AthleticTraining.
     */
    fun madeByAthleticTrainer(): AthleticTrainerId = athleticTrainerId

    /**
     * Retrieves the [MemberId] for which this AthleticTraining has been made.
     * @return The [MemberId] for which this AthleticTraining has been made
     */
    fun madeForMember(): MemberId = memberId

    /**
     * Retrieves the [Purpose] that guides this AthleticTraining.
     * @return The [Purpose] that guides this AthleticTraining.
     */
    fun madeWithPurpose(): Purpose = purpose

    /**
     * Retrieves the [Period] covered by this AthleticTraining.
     * @return The [Period] covered by this AthleticTraining.
     */
    fun coversPeriod(): Period = period

    /**
     * Checks if this AthleticTraining is completed.
     * @return True if this AthleticTraining is completed, false otherwise.
     */
    fun isCompleted() = status == Status.COMPLETED

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
        enforceSchedulingInvariants(schedule)
        if (scheduleOverlaps(schedule)) {
            throw WorkoutScheduleMustNotOverlap()
        }
        scheduledWorkouts = scheduledWorkouts + ScheduledWorkout(workoutId, schedule)
    }

    /**
     * Changes the [Schedule] of an existing [ScheduledWorkout] that matches the provided
     * [id], by using the specified [schedule].
     */
    fun rescheduleWorkout(scheduledWorkoutId: ScheduledWorkoutId, schedule: Schedule) {
        enforceSchedulingInvariants(schedule)
        val scheduledWorkout =
            scheduledWorkouts.firstOrNull { it.id == scheduledWorkoutId }
                ?: throw ScheduledWorkoutNotFound()
        if (scheduleOverlaps(scheduledWorkout, schedule)) {
            throw WorkoutScheduleMustNotOverlap()
        }
        scheduledWorkout.reschedule(schedule)
    }

    /***
     * Enforces scheduling invariants.
     */
    private fun enforceSchedulingInvariants(schedule: Schedule) {
        if (isCompleted()) {
            throw AthleticTrainingAlreadyCompleted()
        }
        if (isScheduleOutOfPeriod(schedule)) {
            throw WorkoutMustBeScheduledDuringPeriodOfTraining()
        }
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
    private fun scheduleOverlaps(schedule: Schedule): Boolean {
        return scheduledWorkouts
            .any { it.scheduledOn().overlapsWith(schedule) }
    }

    /**
     * Checks if the desired [Schedule] overlaps with an existing one, excluding
     * the provided [scheduledWorkout].
     */
    private fun scheduleOverlaps(
        scheduledWorkout: ScheduledWorkout,
        schedule: Schedule
    ): Boolean {
        return scheduledWorkouts
            .filter { it != scheduledWorkout }
            .any { it.scheduledOn().overlapsWith(schedule) }
    }

    /**
     * Completes this AthleticTraining.
     */
    fun complete() {
        status = Status.COMPLETED
    }

    /**
     * Generates a snapshot with the information about this AthleticTraining.
     * @return A [AthleticTrainingSnapshot] with the information about this AthleticTraining.
     */
    fun snapshot() = AthleticTrainingSnapshot(
        id,
        athleticTrainerId,
        memberId,
        purpose,
        period,
        scheduledWorkouts.map { it.snapshot() }
    )
}
