import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
    kotlin("plugin.serialization") version "1.9.10"
}

dependencies {
    implementation(kotlin("stdlib"))

    // Добавьте зависимости для KSP
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.17")

    implementation("com.google.auto.service:auto-service-annotations:1.1.1")

    annotationProcessor("com.google.auto.service:auto-service:1.1.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}
