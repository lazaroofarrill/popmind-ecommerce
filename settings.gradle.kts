pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.22"
    }
}

rootProject.name = "ecommerce"
include("projects:backend:api")
findProject(":projects:backend:api")?.name = "api"
