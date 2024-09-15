import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.pathString

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
}

val appId = "${App.bundleId}.android"

android {
    namespace = appId
    compileSdk = Versions.Android.compileSdk
    defaultConfig {
        applicationId = appId
        minSdk = Versions.Android.minSdk
        targetSdk = Versions.Android.targetSdk
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    sourceSets {
        getByName("debug") {
            manifest.srcFile("build/generated/ksp/debug/resources/StorybookActivityManifest.xml")
        }
    }
}

dependencies {
    implementation(project(":shared:foundation"))
    implementation(project(":shared:app"))
    implementation(project(":shared:features:auth"))
    implementation(project(":shared:features:gates"))
    implementation(libs.bundles.android)
    implementation(project(":story"))
    implementation(libs.runtime.livedata)
    ksp(project(":story"))
}


tasks.register("moveGeneratedFile") {
    group = "build"
    description = "Перемещает файл, сгенерированный KSP, в другую директорию."

    doLast {
        val generatedFilePath = project.buildDir.toPath()
            .resolve("generated/ksp/debug/resources/stories.json")

        println(generatedFilePath.pathString)

        val targetDirectory: Path = Path.of("playground")

        Files.createDirectories(targetDirectory)

        if (Files.exists(generatedFilePath)) {
            Files.move(
                generatedFilePath,
                targetDirectory.resolve(generatedFilePath.fileName),
                StandardCopyOption.REPLACE_EXISTING
            )
            println("File moved to ${targetDirectory.resolve(generatedFilePath.fileName)}")
        } else {
            println("File ${generatedFilePath.fileName} not found!")
        }
    }
}

tasks.whenTaskAdded {
    if (name.startsWith("build") || name.startsWith("assemble")) {
        finalizedBy("moveGeneratedFile")
    }
}