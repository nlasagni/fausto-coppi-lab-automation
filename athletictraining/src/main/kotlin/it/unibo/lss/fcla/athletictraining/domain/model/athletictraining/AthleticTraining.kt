package it.unibo.lss.fcla.athletictraining.domain.model.athletictraining

import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingAlreadyCompleted
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.exception.AthleticTrainingMustHaveMember
import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutMustBeScheduledDuringPeriodOfPreparation
import it.unibo.lss.fcla.athletictraining.domain.exception.WorkoutScheduleMustNotOverlap
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId

/**
 * This is one of the main entities of the Athletic Preparation Bounded Context.
 *
 * An AthleticPreparation must be prepared by an athletic trainer and for a member.
 * It is identified by an auto-generated id based on the athleticTrainerId,
 * memberId and periodOfPreparation properties.
 *
 * The purpose of an AthleticPreparation is to organize the training plans
 * which will bring the member to reach his/her athletic objectives.
 *
 * During a member's AthleticPreparation it is not possible to have two TrainingPlan
 * in the same PeriodOfTraining.
 *
 * The lifecycle of an AthleticPreparation ends when the athletic trainer
 * decides that it is completed.
 *
 * @property athleticTrainerId The id reference of the athletic trainer who is preparing the athletic training.
 * @property memberId The id reference of the member for whom the athletic training is being prepared.
 * @property periodOfPreparation The period of athletic traning. See [PeriodOfPreparation].
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class AthleticTraining(
    private val athleticTrainerId: AthleticTrainerId,
    private val memberId: MemberId,
    private val periodOfPreparation: PeriodOfPreparation
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
     * Returns a unique id of this AthleticPreparation which will be stored
     * into the [id] private property.
     */
    private fun generateId(): AthleticTrainingId =
        AthleticTrainingId("$athleticTrainerId-$memberId-${periodOfPreparation.beginning.dayOfYear}")

    /**
     * Schedules a workout for this AthleticTraining.
     * The workout must not be out of the [periodOfPreparation], otherwise
     * a [WorkoutMustBeScheduledDuringPeriodOfPreparation] exception will be thrown.
     * If another workout in the same date and time already exists, a
     * [WorkoutScheduleMustNotOverlap] exception will be thrown.
     */
    fun scheduleWorkout(workoutId: WorkoutId, schedule: Schedule) {
        if (isAlreadyCompleted()) {
            throw AthleticTrainingAlreadyCompleted()
        }
        if (isScheduleOutOfPeriod(schedule)) {
            throw WorkoutMustBeScheduledDuringPeriodOfPreparation()
        }
        if (scheduleOverlaps(schedule)) {
            throw WorkoutScheduleMustNotOverlap()
        }
        scheduledWorkouts = scheduledWorkouts + ScheduledWorkout(workoutId, schedule)
    }

    /**
     * Checks if the desired [Schedule] of a workout is out of the
     * [periodOfPreparation].
     */
    private fun isScheduleOutOfPeriod(schedule: Schedule): Boolean {
        val scheduleDay = schedule.day
        return scheduleDay.isBefore(periodOfPreparation.beginning) ||
            scheduleDay.isAfter(periodOfPreparation.end)
    }

    /**
     * Checks if the desired [Schedule] overlaps with an existing one.
     */
    private fun scheduleOverlaps(schedule: Schedule): Boolean {
        return scheduledWorkouts.any {
            it.snapshot().schedule.overlaps(schedule)
        }
    }

    /**
     * Completes this AthleticPreparation.
     */
    fun complete() {
        status = Status.COMPLETED
    }

    /**
     * Checks if this AthleticPreparation is completed.
     */
    private fun isAlreadyCompleted() = status == Status.COMPLETED

    /**
     * Generates an [AthleticTrainingSnapshot] with the information about this AthleticPreparation.
     */
    fun snapshot() = AthleticTrainingSnapshot(
        id,
        athleticTrainerId,
        memberId,
        periodOfPreparation,
        scheduledWorkouts
    )
}
