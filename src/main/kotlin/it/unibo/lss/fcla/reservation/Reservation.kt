package it.unibo.lss.fcla.reservation

import java.util.Date

abstract class Reservation (
        val date: Date,
        val member: Member
        ){
    abstract fun updateDate(newDate: Date): Reservation
}