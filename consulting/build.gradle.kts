/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

val myMainClass = "it.unibo.lss.fcla.consulting.StarterApplicationKt"
val excludeFromCoverage = listOf(
    "**/application/**",
    "**/ui/**"
)

ext.set("mainclass", myMainClass)
ext.set("excludeFromCoverage", excludeFromCoverage)
