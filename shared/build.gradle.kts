plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.kotlinVersion
    id("app.cash.sqldelight") version Deps.DB.version
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

            //ViewModel (ios extra)
            with(Deps.ViewModel) {
                export(core)
                export(flow)
            }

        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {

                //network
                with(Deps.Network) {
                    implementation(core)
                    implementation(contentNegotiation)
                    implementation(serialization)
                    implementation(logger)
                }

                //coroutines
                implementation(Deps.Coroutines.core)

                //mvi
                with(Deps.MVI) {
                    implementation(core)
                    implementation(main)
                    implementation(coroutines)
                    implementation(logger)
                }

                //ViewModel
                with(Deps.ViewModel) {
                    api(core)
                    api(flow)
                }

                //DI
                implementation(Deps.DI.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                //network
                implementation(Deps.Network.Client.android)

                //DB
                implementation(Deps.DB.android)
            }
        }

        val iosMain by getting {
            dependencies {
                //network
                implementation(Deps.Network.Client.ios)

                //DB
                implementation(Deps.DB.ios)
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