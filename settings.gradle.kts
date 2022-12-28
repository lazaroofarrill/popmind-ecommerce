val ktorVersion = extra["ktorVersion"] as String

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "1.7.22"
        kotlin("plugin.serialization") version "1.7.22"
        id("com.google.devtools.ksp") version "1.7.22-1.0.8"
        id("io.ktor.plugin") version "2.2.1"
    }
}


rootProject.name = "ecommerce"
include("projects:backend:api")
findProject(":projects:backend:api")?.name = "api"
include("projects:backend:common")
findProject(":projects:backend:common")?.name = "common"
include("projects:backend:popmind")
findProject(":projects:backend:popmind")?.name = "popmind"
