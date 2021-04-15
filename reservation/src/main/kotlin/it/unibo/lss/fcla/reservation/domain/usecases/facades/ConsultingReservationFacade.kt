/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

package it.unibo.lss.fcla.reservation.domain.usecases.facades

import it.unibo.lss.fcla.reservation.domain.entities.reservation.CloseConsultingReservation
import it.unibo.lss.fcla.reservation.domain.entities.reservation.OpenConsultingReservation
import java.util.Date
import java.util.UUID

/**
 * Facade class used to have access to all consulting reservation information.
 *
 * The information are: the [date] of the reservation, the [freelancerId] which is related
 * to the consulting reservation, the [id] of the facade and [isOpen] which is used to know
 * if a reservation is open or close.
 */
data class ConsultingReservationFacade(
    val date: Date,
    val freelancerId: UUID,
    val id: UUID,
    val isOpen: Boolean
) {

    constructor(openConsultingReservation: OpenConsultingReservation) : this(
        openConsultingReservation.date,
        openConsultingReservation.freelancerId.value,
        openConsultingReservation.id,
        isOpen = true
    )

    constructor(closeConsultingReservation: CloseConsultingReservation) : this(
        closeConsultingReservation.date,
        closeConsultingReservation.freelancerId.value,
        closeConsultingReservation.id,
        isOpen = false
    )
}
