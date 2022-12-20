# Welcome to the Starter

This is a template to help you get started building amazing Kotlin applications and libraries. Over time, examples will
be compiled as individual modules, that users can pick and choose from when starting their repo.

This repo loosely follows hexagonal architecture patterns, decoupling the persistence and api layers through a
lightweight domain layer. Feel free to abandon course if this is not your cup of tea.

## Table of Contents

1. [Modules](#modules)
    1. [API](#api)
    2. [App](#app)
    3. [CLI](#cli)
    4. [Client](#client)
    5. [Domain](#domain)
    6. [Persistence](#persistence)
2. [Tooling](#tooling)
    1. [Gradle](#gradle)
    2. [Docker](#docker)

## Modules

Sourdough aims to provide you with a starter point for any type of JVM based Kotlin application you choose to build.

Certain information on module approach will also be detailed in the [gradle](#gradle) section, as there is a lot of
cross over between the modules and the necessary Gradle configuration.

### API

Bare bones [Ktor](https://ktor.io) API server. Since it is wired to the persistence module, you will need to run the
database prior to launch via the `docker-compose up -d` command.

### App

Simple application to run background processes.

### CLI

[Kotlinx CLI](https://github.com/Kotlin/kotlinx-cli) Demo. Includes ability to seed database with pseudo-random data

### Client

TODO

### Domain

Lightweight module containing domain models that enable easy decoupling of API and persistence modules

### Persistence

Lightweight persistence module leveraging Flyway for migrations along with Kotlin Exposed as the ORM. Out of the box it
is configured to point directly at the postgres instance defined in the docker compose file in the root of the project.

## Tooling

### Gradle

This library leverages the [sourdough-gradle](https://github.com/bkbnio/sourdough-gradle) collection of plugins to
autoconfigure a majority of the boilerplate
necessary to get this repo up and running. For more information on all the setup that plugin does, please refer to its
documentation

### Docker

TODO