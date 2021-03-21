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

allprojects {
    repositories {
        jcenter()
    }
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.danilopianini.git-sensitive-semantic-versioning")
    apply(plugin = "org.gradle.jacoco")
    apply(plugin = "pl.droidsonroids.jacoco.testkit")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.gradle.distribution")
    apply(plugin = "org.gradle.application")

    afterEvaluate {
        val mainClassVarName = "mainclass"
        val mainClassName = ext.get(mainClassVarName) as String

        tasks.jar {

            manifest {
                attributes(
                    // Otherwise it throws a "No main manifest attribute" error
                    mapOf(
                        "Main-Class" to mainClassName,
                        "Implementation-Version" to archiveVersion
                    )
                )
            }

            from(sourceSets.main.get().output)
            dependsOn(configurations.runtimeClasspath)

            // Configurations.compile is a reference to the all of the artifacts defined in the compile configuration.
            from({
                // Wrap each of the JAR files with the zipTree() method.
                // The result is a collection of ZIP file trees
                configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
            })
        }

        application {
            mainClass.set(mainClassName)
        }
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

    gitSemVer {
        version = computeGitSemVer()
    }

    detekt {
        failFast = true
        buildUponDefaultConfig = true
        config = files(project.rootDir.resolve("detektConfig.yml"))
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
}
