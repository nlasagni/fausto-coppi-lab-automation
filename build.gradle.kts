plugins {
    //In order to build a Kotlin project with Gradle:
    kotlin("jvm")
    // A Gradle plugin that forces semantic versioning and relies on git to detect the project state
    id("org.danilopianini.git-sensitive-semantic-versioning")
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
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    // Define the main class for the application
    mainClass.set("faustoCoppiLabAutomation.MainClassKt")
}
