/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.athletictraining.usecase.fclat1

import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTraining
import it.unibo.lss.fcla.athletictraining.domain.model.activeathletictraining.ActiveAthleticTrainingId
import it.unibo.lss.fcla.athletictraining.domain.service.MemberProfileUpdater
import it.unibo.lss.fcla.athletictraining.domain.service.OverlappingAthleticTrainingsChecker
import it.unibo.lss.fcla.athletictraining.domain.shared.Period
import it.unibo.lss.fcla.athletictraining.domain.shared.Purpose
import it.unibo.lss.fcla.athletictraining.usecase.shared.exception.OverlappingAthleticTraining
import it.unibo.lss.fcla.athletictraining.usecase.shared.input.AthleticTrainingManagement
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.ActiveAthleticTrainingRepository
import it.unibo.lss.fcla.athletictraining.usecase.shared.output.IdGenerator

/**
 * Plans a new athletic training, refers to requirement FCLAT-1.
 *
 * @author Nicola Lasagni on 06/03/2021.
 */
class Fclat1PlanAthleticTraining(
    useCaseOutput: PlanAthleticTrainingOutput,
    private val idGenerator: IdGenerator,
    private val memberProfileUpdater: MemberProfileUpdater,
    private val repository: ActiveAthleticTrainingRepository,
    private val overlappingService: OverlappingAthleticTrainingsChecker
) : PlanAthleticTrainingInput,
    AthleticTrainingManagement<PlanActiveAthleticTrainingRequest, ActiveAthleticTraining>(useCaseOutput) {

    override fun processRequest(request: PlanActiveAthleticTrainingRequest): ActiveAthleticTraining {
        val period = Period(request.startDay, request.endDay)
        val memberAthleticTrainings =
            repository.findAllByMemberId(request.memberId).map { ActiveAthleticTraining.rehydrate(it) }
        if (overlappingService.existsOverlappingAthleticTraining(memberAthleticTrainings, period)) {
            throw OverlappingAthleticTraining()
        }
        val athleticTraining = ActiveAthleticTraining(
            ActiveAthleticTrainingId(idGenerator.generate()),
            request.athleticTrainerId,
            request.memberId,
            Purpose(request.purpose),
            period
        )
        repository.add(athleticTraining.snapshot())
        memberProfileUpdater.updateProfile(athleticTraining.member)
        return athleticTraining
    }
}
