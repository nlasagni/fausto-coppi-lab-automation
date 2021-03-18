package it.unibo.lss.fcla.reservation.domain.entities.exceptions

/**
 * [Exception] that occur when a consulting reservation is created without a freelancer
 */
class FreelancerIdCannotBeEmpty :
    Exception("A Consulting Reservation must have a Freelancer.")
