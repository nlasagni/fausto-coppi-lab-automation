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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = JavaVersion.VERSION_1_8.toString()
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
    failFast = true
    buildUponDefaultConfig = true
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
    mainClass.set("it.unibo.lss.fcla.MainClassKt")
}
