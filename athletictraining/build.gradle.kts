val myMainClass = "it.unibo.lss.fcla.athletictraining.AthleticTrainingApplicationStarterKt"
val excludeFromCoverage = listOf(
    "**/adapter/controller/**",
    "**/adapter/presenter/**",
    "**/config/**",
    "**/infrastructure/**"
)

ext.set("mainclass", myMainClass)
ext.set("excludeFromCoverage", excludeFromCoverage)

