/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining

import it.unibo.lss.fcla.athletictraining.domain.model.athletictrainer.AthleticTrainerId
import it.unibo.lss.fcla.athletictraining.domain.model.completedathletictraining.exception.CompletedAthleticTrainingIdMissing
import it.unibo.lss.fcla.athletictraining.domain.model.member.MemberId
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.AthleticTrainingMustHaveAthleticTrainer
import it.unibo.lss.fcla.athletictraining.domain.shared.exception.AthleticTrainingMustHaveMember
import java.time.LocalDateTime

/**
 * This is the Aggregate Root of the Completed Athletic Training aggregate.
 *
 * Represents a completed athletic training which has been prepared by an
 * athletic trainer and for a member.
 *
 * @property id The unique [CompletedAthleticTrainingId] of this [CompletedAthleticTraining].
 *
 * @author Nicola Lasagni on 05/04/2021.
 */
class CompletedAthleticTraining(
    val id: CompletedAthleticTrainingId,
    private val athleticTrainer: AthleticTrainerId,
    private val member: MemberId,
    private val purpose: Purpose,
    private val period: Period,
    private val completedWorkouts: Collection<CompletedWorkout>
) {

    private val completionDateTime: LocalDateTime

    init {
        if (id.value.isEmpty()) {
            throw CompletedAthleticTrainingIdMissing()
        }
        if (athleticTrainer.value.isEmpty()) {
            throw AthleticTrainingMustHaveAthleticTrainer()
        }
        if (member.value.isEmpty()) {
            throw AthleticTrainingMustHaveMember()
        }
        completionDateTime = LocalDateTime.now()
    }

    /**
     * Retrieves the [LocalDateTime] when the athletic training was completed.
     * @return The [LocalDateTime] when the athletic training was completed.
     */
    fun completedAt(): LocalDateTime = completionDateTime

    /**
     * Retrieves the [AthleticTrainerId] that made this AthleticTraining.
     * @return The [AthleticTrainerId] that made this AthleticTraining.
     */
    fun madeByAthleticTrainer(): AthleticTrainerId = athleticTrainer

    /**
     * Retrieves the [MemberId] for which this AthleticTraining has been made.
     * @return The [MemberId] for which this AthleticTraining has been made
     */
    fun madeForMember(): MemberId = member

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
     * Retrieves the collection of [CompletedWorkout] during this AthleticTraining.
     * @return The collection of [CompletedWorkout] during this AthleticTraining.
     */
    fun workouts(): Collection<CompletedWorkout> = completedWorkouts

    override fun toString(): String {
        return "CompletedAthleticTraining(id=$id, " +
            "athleticTrainer=$athleticTrainer, " +
            "member=$member, purpose=$purpose, " +
            "period=$period, " +
            "completedWorkouts=$completedWorkouts, " +
            "completionDateTime=$completionDateTime)"
    }
}
