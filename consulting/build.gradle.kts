val myMainClass = "it.unibo.lss.fcla.consulting.StarterApplicationKt"
val excludeFromCoverage = listOf(
    "**/application/**",
    "**/ui/**"
)

ext.set("mainclass", myMainClass)
ext.set("excludeFromCoverage", excludeFromCoverage)
