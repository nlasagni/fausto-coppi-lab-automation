package it.unibo.lss.fcla.reservation.domain.entities.exceptions

/**
 * [Exception] that occur when a workout reservation is created without an aim
 */
class AimCannotBeEmpty : Exception("A workout reservation aim cannot be empty")
