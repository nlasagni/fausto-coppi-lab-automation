package it.unibo.lss.fcla.athleticPreparation.domain.model

import java.util.Date

class MembershipCard(val code: String, val dueDate: Date) {

    fun isActive() = dueDate >= Date(System.currentTimeMillis())
}
