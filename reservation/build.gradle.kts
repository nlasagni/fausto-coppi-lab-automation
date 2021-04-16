val myMainClass = "it.unibo.lss.fcla.reservation.InteractiveReservationMicroserviceStarterClassKt"
val excludeFromCoverage = listOf(
    "**/application/**",
    "**/infrastructure/**",
    "**/ui/**",
    "**/presentation/**"
)

ext.set("mainclass", myMainClass)
ext.set("excludeFromCoverage", excludeFromCoverage)
