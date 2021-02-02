package it.unibo.lss.fcla.athleticPreparation.domain.model

import java.util.Date

/**
 *
 */
data class CardType(val type: String = "")

class MembershipCard(val code: String, val dueDate: Date, val type: CardType) {

    fun isActive() = dueDate >= Date(System.currentTimeMillis())

    fun getCardType() : CardType = type.copy()
}
