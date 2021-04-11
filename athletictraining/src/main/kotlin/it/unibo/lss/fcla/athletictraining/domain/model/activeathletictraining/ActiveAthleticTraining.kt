package it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception.ActiveAthleticTrainingIdMissing
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception.PeriodExtensionCannotEndBeforeCurrentPeriod
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception.ScheduledWorkoutNotFound
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception.WorkoutMustBeScheduledDuringPeriodOfTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.exception.WorkoutScheduleMustNotOverlap
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.model.workout.WorkoutId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.domain.shared.Schedule
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.AthleticTrainingMustHaveMember
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * This is the Aggregate Root of the Active Athletic Training aggregate.
 *
 * An ActiveAthleticTraining must be prepared by an athletic trainer and for a member.
 *
 * The purpose of an ActiveAthleticTraining is to organize the workouts
 * which will bring the member to reach his/her athletic objectives.
 *
 * During a member's ActiveAthleticTraining it is not possible to have two Workouts
 * with overlapping [Schedule]s.
 *
 * @property id The unique [ActiveAthleticTrainingId] of this ActiveAthleticTraining.
 * @property athleticTrainer The id reference of the athletic trainer who is preparing the athletic training.
 * @property member The id reference of the member for whom the athletic training is being prepared.
 * @property purpose The purpose of the athletic training. See [Purpose].
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
class ActiveAthleticTraining(
    val id: ActiveAthleticTrainingId,
    val athleticTrainer: AthleticTrainerId,
    val member: MemberId,
    val purpose: Purpose,
    private var period: Period
) {

    companion object {
        fun rehydrate(snapshot: ActiveAthleticTrainingSnapshot): ActiveAthleticTraining {
            val activeAthleticTraining = ActiveAthleticTraining(
                snapshot.id,
                snapshot.athleticTrainer,
                snapshot.member,
                snapshot.purpose,
                snapshot.period
            )
            for (scheduledWorkout in snapshot.scheduledWorkouts) {
                activeAthleticTraining.scheduleWorkout(
                    scheduledWorkout.workout,
                    scheduledWorkout.schedule
                )
            }
            return activeAthleticTraining
        }
    }

    private var scheduledWorkouts: List<ScheduledWorkout> = emptyList()

    init {
        if (id.value.isEmpty()) {
            throw ActiveAthleticTrainingIdMissing()
        }
        if (athleticTrainer.value.isEmpty()) {
            throw AthleticTrainingMustHaveAthleticTrainer()
        }
        if (member.value.isEmpty()) {
            throw AthleticTrainingMustHaveMember()
        }
    }

    /**
     * Postpones the [period] of this athletic training.
     * If the [postponedEnd] ends before the current [period],
     * throws a [PeriodExtensionCannotEndBeforeCurrentPeriod].
     */
    fun postponeTrainingPeriodEnd(postponedEnd: LocalDate) {
        val postponedPeriod = Period(period.beginningDay(), postponedEnd)
        if (!postponedPeriod.endsAfter(period)) {
            throw PeriodExtensionCannotEndBeforeCurrentPeriod()
        }
        period = postponedPeriod
    }

    /**
     * Checks if the specified [period] overlaps with an existing one.
     * @return True if the specified [period] overlaps with the one of this athletic training, false otherwise.
     */
    fun overlapsWithPeriod(period: Period): Boolean {
        return this.period.overlapsWith(period)
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
     * Changes the [currentSchedule] of the specified [workout] with the desired [newSchedule].
     */
    fun rescheduleWorkout(
        workout: WorkoutId,
        currentSchedule: Schedule,
        newSchedule: Schedule
    ) {
        enforceSchedulingInvariants(newSchedule)
        val scheduledWorkout =
            scheduledWorkouts.firstOrNull { it.id == ScheduledWorkoutId(workout, currentSchedule) }
                ?: throw ScheduledWorkoutNotFound()
        val scheduleOverlaps = scheduledWorkouts
            .filter { it != scheduledWorkout }
            .any { it.scheduledOn().overlapsWith(newSchedule) }
        if (scheduleOverlaps) {
            throw WorkoutScheduleMustNotOverlap()
        }
        scheduledWorkout.reschedule(newSchedule)
    }

    /**
     * Cancels the [workout] that has the specified [schedule] in this athletic training.
     */
    fun cancelScheduledWorkout(workout: WorkoutId, schedule: Schedule) {
        val scheduledWorkoutToCancel =
            scheduledWorkouts.firstOrNull { it.id == ScheduledWorkoutId(workout, schedule) }
                ?: throw ScheduledWorkoutNotFound()
        scheduledWorkouts = scheduledWorkouts - scheduledWorkoutToCancel
    }

    /***
     * Enforces scheduling invariants.
     */
    private fun enforceSchedulingInvariants(schedule: Schedule) {
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
        return scheduleEndDateTime.isAfter(period.endDateTime())
    }

    /**
     * Checks if the desired [Schedule] overlaps with an existing one.
     */
    private fun scheduleOverlaps(schedule: Schedule): Boolean {
        return scheduledWorkouts
            .any { it.scheduledOn().overlapsWith(schedule) }
    }

    /**
     * Generates a snapshot with the information about this AthleticTraining.
     * @return A [ActiveAthleticTrainingSnapshot] with the information about this AthleticTraining.
     */
    fun snapshot() = ActiveAthleticTrainingSnapshot(
        id,
        athleticTrainer,
        member,
        purpose,
        period,
        scheduledWorkouts.map { it.snapshot() }
    )

    override fun toString(): String {
        return "ActiveAthleticTraining(id=$id," +
            "athleticTrainer=$athleticTrainer, " +
            "member=$member, " +
            "purpose=$purpose, " +
            "period=$period, " +
            "scheduledWorkouts=$scheduledWorkouts)"
    }
}
