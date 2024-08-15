@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version libs.versions.kotlin
    id("app.cash.sqldelight") version libs.versions.sqldelight
}

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
        podfile = project.file("../iosApp/Podfile")
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
                implementation(libs.bundles.shared.common)

                // ViewModel (common extra)
                api(libs.mvvm.core)
                api(libs.mvvm.flow)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.bundles.shared.android)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.bundles.shared.ios)
            }
        }
    }
}

android {
    namespace = App.bundleId
    compileSdk = Versions.Android.compileSdk
    defaultConfig {
        minSdk = Versions.Android.minSdk
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set(App.bundleId)
        }
    }
}