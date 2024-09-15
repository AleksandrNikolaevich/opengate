pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "OpenGate"
include(":androidApp")
include(":shared:app")
include(":shared:foundation")
include(":shared:features:auth")
include(":shared:features:gates")
include(":story")