package it.unibo.lss.fcla.reservation

import java.util.Date

abstract class Reservation(open val date: Date, open val member: Member) {
    abstract fun updateDate(newDate: Date): Reservation
}
