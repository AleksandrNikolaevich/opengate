@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

var packageName = App.bundleId + ".features.gates"

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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.bundles.shared.feature.core)
                implementation(libs.bundles.shared.feature.gates)

                implementation(project(":shared:foundation"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
//                implementation(libs.bundles.shared.android)
            }
        }

        val iosMain by getting {
            dependencies {
//                implementation(libs.bundles.shared.ios)
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
