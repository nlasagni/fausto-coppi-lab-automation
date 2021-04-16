/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

/**
 * @project fausto-coppi-lab-automation
 * @author Alessia Cerami and Andrea Giordano
 */

val myMainClass = "it.unibo.lss.fcla.reservation.InteractiveReservationMicroserviceStarterClassKt"
val excludeFromCoverage = listOf(
    "**/application/**",
    "**/infrastructure/**",
    "**/ui/**",
    "**/presentation/**"
)

ext.set("mainclass", myMainClass)
ext.set("excludeFromCoverage", excludeFromCoverage)
