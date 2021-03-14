val myMainClass = "it.unibo.lss.fcla.MainClassKt"

plugins {
    kotlin("jvm")
    id("org.danilopianini.git-sensitive-semantic-versioning")
    id("pl.droidsonroids.jacoco.testkit")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
    jacoco
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.50")
    testImplementation(gradleTestKit())
    testImplementation("io.kotest:kotest-runner-junit5:4.2.5")
    testImplementation("io.kotest:kotest-assertions-core:4.2.5")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.2.5")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.14.1")
}

detekt {
    failFast = true
    buildUponDefaultConfig = true
    config = files("detektConfig.yml")
}

tasks.jar {
    // Otherwise it throws a "No main manifest attribute" error
    manifest {
        attributes(
            mapOf(
                "Main-Class" to myMainClass,
                "Implementation-Version" to archiveVersion
            )
        )
    }
    // All the dependencies needed by our application doesn't exists in the same classpath where our .jar resides.
    // In order to make our application standalone, we must include all of its dependencies inside our application.
    // Include all of its dependencies inside our application

    // To add all of the dependencies otherwise a "NoClassDefFoundError" error
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)

    // Configurations.compile is a reference to the all of the artifacts defined in the compile configuration.
    from({
        // Wrap each of the JAR files with the zipTree() method. The result is a collection of ZIP file trees

        // configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

