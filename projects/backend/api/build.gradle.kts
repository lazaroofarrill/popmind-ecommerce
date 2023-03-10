val ktorVersion by properties
val koinVersion by properties
val logbackVersion by properties
val oryKratosVersion by properties

val exposed_version: String by project
val h2_version: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("io.ktor.plugin")
    application
}

group = "com.espoletatecnologias"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")
    implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
    implementation("io.ktor:ktor-server-freemarker:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")

    //ktor client
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")


    implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.459")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Koin Core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.ktor:ktor-server-freemarker-jvm:2.2.1")
    implementation("io.ktor:ktor-server-openapi:2.2.1")
    implementation("io.ktor:ktor-client-logging-jvm:2.2.1")
    implementation(project(mapOf("path" to ":projects:backend:common")))
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    //Ory clients
    implementation("sh.ory.kratos:kratos-client:$oryKratosVersion")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    //exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("com.h2database:h2:$h2_version")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("com.espoletatecnologias.api.MainKt")
    val developmentMode by properties
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$developmentMode")
}

ktor {
    fatJar {
        archiveFileName.set("api.jar")
    }
}
