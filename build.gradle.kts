/*******************************************************************************
 * Copyright (C) 2021, Stefano Braggion, Alessia Cerami, Andrea Giordano, Nicola Lasagni.
 *
 * This file is part of Fausto Coppi Lab Automation, and is distributed under the terms of the
 * GNU General Public License, as described in the file LICENSE in the
 * Fausto Coppi Lab Automation distribution's top directory.
 *
 ******************************************************************************/

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

val mainClassVarName = "mainclass"
val excludeVarName = "excludeFromCoverage"
val subprojectsDistributionDir = "${rootProject.buildDir}/all-distributions"

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

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
        testImplementation(gradleTestKit())
        testImplementation("io.kotest:kotest-runner-junit5:_")
        testImplementation("io.kotest:kotest-assertions-core:_")
        testImplementation("io.kotest:kotest-assertions-core-jvm:_")
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")
    }

    tasks.getByName<JavaExec>("run") {
        standardInput = System.`in`
    }

    tasks.getting(JavaExec::class) {
        standardInput = System.`in`
    }

    afterEvaluate {
        val mainClassName = ext.get(mainClassVarName) as String
        val excludesVal = if (ext.has(excludeVarName)) ext.get(excludeVarName) as List<String> else emptyList()

        tasks.jacocoTestReport {
            reports {
                xml.isEnabled = true
                html.isEnabled = true
            }
            classDirectories.setFrom(
                classDirectories.files.map {
                    fileTree(it).matching {
                        exclude(excludesVal)
                    }
                }
            )
        }

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

        tasks.assembleDist {
            doLast {
                copy {
                    from("$buildDir/distributions")
                    into(subprojectsDistributionDir)
                }
            }
        }

        application {
            mainClass.set(mainClassName)
        }
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

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            allWarningsAsErrors = true
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

val jacocoAggregatedReport by tasks.creating(JacocoReport::class) {
    var classDirs: FileCollection = files()
    subprojects.forEach {
        dependsOn(it.tasks.test)
        dependsOn(it.tasks.jacocoTestReport)
        sourceSets(it.sourceSets.main.get())
        classDirs += files(it.tasks.jacocoTestReport.get().classDirectories)
    }
    classDirectories.setFrom(classDirs)
    executionData.setFrom(
        fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec")
    )
    reports {
        xml.isEnabled = true
        html.isEnabled = true
        csv.isEnabled = false
    }
}
