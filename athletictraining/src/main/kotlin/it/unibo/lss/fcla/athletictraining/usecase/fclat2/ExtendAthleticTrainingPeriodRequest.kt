package it.unibo.lss.fcla.athletictraining.usecase.fclat2

import java.time.LocalDate

/**
 * Class that represents the request coming from outer layer of
 * extending an athletic training period.
 *
 * @author Nicola Lasagni on 09/04/2021.
 */
data class ExtendAthleticTrainingPeriodRequest(val id: String, val newPeriodEnd: LocalDate)
