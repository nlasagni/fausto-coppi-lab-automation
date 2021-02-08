val myMainClass = "it.unibo.lss.fcla.MainClassKt"

plugins {
    // In order to build a Kotlin project with Gradle:
    kotlin("jvm")
    // A Gradle plugin that forces semantic versioning and relies on git to detect the project state
    id("org.danilopianini.git-sensitive-semantic-versioning")
    jacoco
    id("pl.droidsonroids.jacoco.testkit")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
    application
}

gitSemVer {
    version = computeGitSemVer()
}

repositories {
    jcenter()
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

tasks.withType<Test> {
    useJUnitPlatform() // Use JUnit 5 engine
    testLogging.showStandardStreams = true
    testLogging {
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.register("computeVersion") {
    val reg = Regex("Version: \\[([0-9]+\\.){2}[0-9]+(-[0-9a-zA-Z-+]*)?]")
    val readme = File("$projectDir/README.md")
    val text = readme.readText()
    if (reg.containsMatchIn(text)) {
        readme.writeText(text.replace(reg, "Version: [$version]"))
    } else {
        readme.appendText("Version: [$version]\n")
    }
}

application {
    // Define the main class for the application
    mainClass.set(myMainClass)
}
