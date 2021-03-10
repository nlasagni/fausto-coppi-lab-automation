package it.unibo.lss.fcla.reservation.domain.entities.exceptions

/**
 * [Exception] that occur when a consulting summary is created with invalid data
 */
class MemberDataMustNotBeEmpty : Exception("You haven't entered all the required data. Insert it.")
