plugins {
    //In order to build a Kotlin project with Gradle:
    kotlin("jvm")
    // A Gradle plugin that forces semantic versioning and relies on git to detect the project state
    id("org.danilopianini.git-sensitive-semantic-versioning")
    jacoco
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    application
}

gitSemVer {
    version = computeGitSemVer()
}

repositories {
    jcenter{
        content { onlyForConfigurations("detekt") }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation(gradleTestKit())
    testImplementation("io.kotest:kotest-runner-junit5:4.2.5")
    testImplementation("io.kotest:kotest-assertions-core:4.2.5")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.2.5")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.14.1")
}

detekt {
    failFast = true // fail build on any finding
    buildUponDefaultConfig = true // preconfigure defaults
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

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.register("generateVersionFile") {
    File("$buildDir/version").writeText(version.toString())
}

application {
    // Define the main class for the application
    mainClass.set("faustoCoppiLabAutomation.MainClassKt")
}
