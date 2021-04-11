package it.unibo.lss.fcla.athletictraining.infrastructure.ui

import it.unibo.lss.fcla.athletictraining.adapter.controller.ControllerInput

/**
 * @author Nicola Lasagni on 11/04/2021.
 */
interface View {

    fun registerController(controller: ControllerInput)

    fun start()
}
