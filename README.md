# Fausto Coppi Lab Automation

![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/nlasagni/fausto-coppi-lab-automation)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/nlasagni/fausto-coppi-lab-automation/workflow)
<a href="https://nlasagni.github.io/fausto-coppi-lab-automation/fausto-coppi-lab-automation/"><img src="https://img.shields.io/badge/docs%20by-Dokka-green.svg"/></a>
![GitHub](https://img.shields.io/github/license/nlasagni/fausto-coppi-lab-automation)

- Main coverage: [![maincodecov](https://codecov.io/gh/nlasagni/fausto-coppi-lab-automation/branch/main/graph/badge.svg)](https://codecov.io/gh/nlasagni/fausto-coppi-lab-automation/branch/main)
- Develop coverage: [![developcodecov](https://codecov.io/gh/nlasagni/fausto-coppi-lab-automation/branch/develop/graph/badge.svg)](https://codecov.io/gh/nlasagni/fausto-coppi-lab-automation/branch/develop)

## Disclaimer

**Since it was not the purpose of the exam to develop a system in its entirety, the software released so far is still 
to be considered in the prototype phase.**

## Overview

Exam project for the Laboratory of Software Systems (LSS) course of the Computer Science and Engineering Master degree 
in Cesena. 

*Fausto Coppi Lab Automation* is a system designed to automate the management of the *Fausto Coppi Lab*, which is a
part of the Fausto Coppi Building, the new headquarters of the [Fausto Coppi](https://gcfaustocoppi.it/) company.

The *Fausto Coppi Lab* represents the main service for athletes, amateur cyclists, other members and employees of the 
company, as it provides a gym, physiotherapy, and analysis and measurement of cycling posture (biomechanics).

## Project Documentation

Project Report and DevOps documentation reside into Confluence space.

- [Check out the Project Report](https://stefanobraggion.atlassian.net/wiki/spaces/LSS/overview)
- [Check out DevOps documentation](https://stefanobraggion.atlassian.net/l/c/qVfgiRaV "Confluence DevOps page")

## Code Documentation

Code documentation is available through the repository GitHub Pages space.

[Check out code documentation](https://nlasagni.github.io/fausto-coppi-lab-automation/fausto-coppi-lab-automation/)

## Code Coverage

Code coverage reports are made available through the Codecov service (see also badges above).

[Check out code coverage](https://app.codecov.io/gh/nlasagni/fausto-coppi-lab-automation)

## Project Structure

The project consists of three microservices:

- `athletictraining`
- `consulting`
- `reservation`

They relate to the homonyms [bounded context](https://stefanobraggion.atlassian.net/l/c/WhJSfiwp) identified during the 
analysis and design phases of the project.

Each microservice implement specific features which have been identified throughout the analysis phase, 
for further information [see the related Confluence page](https://stefanobraggion.atlassian.net/l/c/1zHykXhG).

In Gradle, this means that the project consists of three suitably configured subprojects.

## Getting started

#### Prerequisites

- Java 1.8

#### Clone project

```bash
$ git clone https://github.com/nlasagni/fausto-coppi-lab-automation.git
$ cd fausto-coppi-lab-automation
```

#### Build from source code

All microservices:

```bash
$ gradlew build
```

Specific microservice:

```bash
$ gradlew <microservice>:build
```

#### Run microservices in interactive mode

Each microservice can run in interactive mode in order to demonstrate the feature that implements.

```
$ gradlew <microservice>:run -q --console=plain
```

#### Test

All microservices:

```bash
$ gradlew test
```

Specific microservice:

```bash
$ gradlew <microservice>:test
```

#### Generate documentation

Since the project is structured in subprojects, in order to generate the documentation of the entire project 
it is necessary to use the following task provided by dokka.

```
$ gradlew dokkaHtmlCollector
```

The result can be found in `build/dokka/htmlCollector/` inside the root folder 
of the project.

#### Generate coverage report

We created a custom Gradle task in order to generate the coverage report of the entire project.

```
$ gradlew jacocoAggregatedReport
```

The result can be found in `build/reports/jacoco/jacocoAggregatedReport/` inside the root folder
of the project.

## Authors
- [Stefano Braggion](https://github.com/sbraggion)
- [Alessia Cerami](https://github.com/NarcAle)
- [Andrea Giordano](https://github.com/Giordo94)
- [Nicola Lasagni](https://github.com/nlasagni)