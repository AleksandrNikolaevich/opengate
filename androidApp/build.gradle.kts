plugins {
    id("com.android.application")
    kotlin("android")
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
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
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
}

dependencies {
    implementation(project(":shared"))
    with(Deps.AndroidX.Compose) {
        implementation(ui)
        implementation(foundation)
        implementation(activity)
        implementation(systemUiController)
    }

    with(Deps.AndroidX.Compose.Tooling) {
        implementation(ui)
        implementation(preview)
    }

    with(Deps.AndroidX.Compose.Material) {
        implementation(core)
        implementation(icons)
    }

    with(Deps.AndroidX.Compose.Navigation) {
        implementation(core)
    }

    //DI
    implementation(Deps.DI.compose)
}