val myMainClass = "it.unibo.lss.fcla.athletictraining.AthleticTrainingApplicationStarterKt"

ext.set("mainclass", myMainClass)

val infrastructurePackage = "infrastructure"
val adapterPackage = "adapter"
val configPackage = ""
val starterApplicationClass = ""

tasks.jacocoTestReport {
    classDirectories.setFrom(
        sourceSets.getByName("main").output.asFileTree.matching {

            exclude(
                "**/adapter/**",
                "**/config/**",
                "**/infrastructure/**",
                ""
            )
        }
//        files(classDirectories.files.map {
//            fileTree(it) {
//                logger.log(LogLevel.WARN, "Directory is: ${it.absolutePath}")
//                exclude(
//                    "**/adapter/**",
//                    "**/config/**",
//                    "**/infrastructure/**",
//                    ""
//                )
//            }
//        })
    )
}
