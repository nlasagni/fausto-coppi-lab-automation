package it.unibo.lss.fcla.athletictraining.domain.shared

import it.unibo.lss.fcla.athletictraining.domain.shared.exception.PurposeMustNotBeEmpty

/**
 * The purpose that an athletic trainer wants a member to reach
 * through an athletic training.
 *
 * @author Nicola Lasagni on 22/02/2021.
 */
data class Purpose(val description: String) {

    init {
        if (description.isEmpty()) {
            throw PurposeMustNotBeEmpty()
        }
    }
}
