val ktorVersion by properties
val koinVersion by properties

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
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

    // Koin Core features
    implementation("io.insert-koin:koin-core:$koinVersion")
// Koin Test features
    testImplementation("io.insert-koin:koin-test:$koinVersion")

// Koin for JUnit 4
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")
// Koin for JUnit 5
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
// Koin for Ktor
    implementation("io.insert-koin:koin-ktor:$koinVersion")
// SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {

    mainClass.set("com.espoletatecnologias.api.MainKt")
}
