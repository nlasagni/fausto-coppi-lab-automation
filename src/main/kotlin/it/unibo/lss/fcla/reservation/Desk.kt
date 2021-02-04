package it.unibo.lss.fcla.reservation

import java.util.Date

/**
 * Class used to menage reservations
 */
class Desk {

    private var reservation: MutableList<Reservation> = mutableListOf()

    /**
     * Method used to create a new Gym Reservation.
     *
     * @param date The date of the reservation
     * @param member Who wants to take a reservation
     * @param machine Optionally, the gym equipment to be reserved
     */
    fun createGymReservation(date: Date, member: Member, machine: Machine?): ReservationGym {
        val res = ReservationGym(date, member, machine)
        reservation.add(res)
        return res
    }

    /**
     * Method used to create a new Consulting Reservation.
     *
     * @param date The date of the reservation
     * @param member Who wants to take a reservation
     * @param professional The professional figure requested into the reservation
     * @param consulting The specific consulting requested
     */
    fun createConsultingReservation(date: Date, member: Member, professional: Professional, consulting: Consulting):
        ReservationConsulting {
            val res = ReservationConsulting(date, member, professional, consulting)
            reservation.add(res)
            return res
        }

    /**
     * Method used to research a specific reservation or a list of reservations
     *
     * @param member Optionally, who made a reservation
     * @param date Optionally, the date of the reservation
     *
     * @return A list of reservations with the specific member and/or date
     */
    fun read(member: Member? = null, date: Date? = null): List<Reservation> {
        return reservation
            .filter { member?.equals(it.member) ?: true }
            .filter { date?.equals(it.date) ?: true }
    }

    /**
     * Method used to update a specific reservation
     *
     * @param reservation The reservation to be updated
     * @param date The new date of the reservation
     */
    fun update(reservation: Reservation, date: Date) {
        this.reservation = this.reservation.map {
            if (it == reservation) it.updateDate(date) else it
        } as MutableList<Reservation>
    }

    /**
     * Method used to delete a specific reservation
     *
     * @param reservation The reservation to be deleted
     */
    fun delete(reservation: Reservation) {
        this.reservation.remove(reservation)
    }
}
