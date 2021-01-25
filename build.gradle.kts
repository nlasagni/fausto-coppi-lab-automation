plugins {
    //In order to build a Kotlin project with Gradle:
    kotlin("jvm") version "1.4.21"
    application
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
