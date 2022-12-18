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
    }
}


rootProject.name = "ecommerce"
include("projects:backend:api")
findProject(":projects:backend:api")?.name = "api"
