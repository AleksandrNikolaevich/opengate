import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

var packageName = App.bundleId + ".app"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../../iosApp/Podfile")
        name = "shared"
        framework {
            baseName = "MultiPlatformLibrary"

            // ViewModel (ios extra)
            export(libs.mvvm.core)
            export(libs.mvvm.flow)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.bundles.shared.app)

                // ViewModel (common extra)
                api(libs.mvvm.core)
                api(libs.mvvm.flow)

                // Modules
                implementation(project(":shared:foundation"))
                implementation(project(":shared:features:auth"))
                implementation(project(":shared:features:gates"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
            }
        }

        val iosMain by getting {
            dependencies {
            }
        }
    }
}

android {
    namespace = packageName
    compileSdk = Versions.Android.compileSdk
    defaultConfig {
        minSdk = Versions.Android.minSdk
    }
}

// Issue https://github.com/touchlab/SQLiter/issues/77
project.extensions.findByType(KotlinMultiplatformExtension::class.java)?.apply {
    targets
        .filterIsInstance<KotlinNativeTarget>()
        .flatMap { it.binaries }
        .forEach { compilationUnit -> compilationUnit.linkerOpts("-lsqlite3") }
}
