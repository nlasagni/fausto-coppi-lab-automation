package it.unibo.lss.fcla.reservation

import java.util.Date

/**
 * The Reservation class used as abstract class that will be inherited by ReservationGym and ReservationConsulting
 *
 * @param date The date of a reservation
 * @param member Who takes a reservation
 */
abstract class Reservation(open val date: Date, open val member: Member) {
    /**
     * Method used to update a reservation
     *
     * @param newDate The new date of the reservation
     *
     * @return A new reservation
     */
    abstract fun updateDate(newDate: Date): Reservation
}
