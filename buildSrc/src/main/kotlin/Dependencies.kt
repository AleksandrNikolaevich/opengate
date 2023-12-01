object App {
    const val bundleId = "ru.kode.tools.opengate"
}

object Versions {
    object Android {
        const val compileSdk = 34
        const val targetSdk = compileSdk
        const val minSdk = 24
    }

    object Ios {
        const val podVersion = "1.0"

        const val deploymentTarget = "14.1"
    }

    const val kotlinVersion = "1.9.10"

    const val kotlinCompilerExtensionVersion = "1.5.3"
}

object Deps {
    object AndroidX {
        object Compose {
            private const val version = "1.4.3"

            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val ui = "androidx.compose.ui:ui:$version"

            const val activity = "androidx.activity:activity-compose:1.6.1"

            const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:0.32.0"

            object Tooling {
                const val ui = "androidx.compose.ui:ui-tooling:$version"
                const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
            }

            object Material {
                const val core = "androidx.compose.material3:material3:1.2.0-alpha11"
                const val icons = "androidx.compose.material:material-icons-extended:1.5.2"
            }
        }
    }

    object Network {
        private const val version = "2.3.2"

        const val core = "io.ktor:ktor-client-core:$version"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val logger = "io.ktor:ktor-client-logging:$version"

        object Client {
            const val android = "io.ktor:ktor-client-android:$version"
            const val ios = "io.ktor:ktor-client-darwin:$version"
        }
    }

    object Coroutines {
        private const val version = "1.7.3"

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    }
    
    object MVI {
        private const val version = "3.2.1"

        const val core = "com.arkivanov.mvikotlin:mvikotlin:$version"
        const val main = "com.arkivanov.mvikotlin:mvikotlin-main:$version"
        const val coroutines = "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:$version"
        const val logger = "com.arkivanov.mvikotlin:mvikotlin-logging:$version"
    }
    
    object ViewModel {
        private const val version = "0.16.1"

        const val core = "dev.icerock.moko:mvvm-core:$version"
        const val flow = "dev.icerock.moko:mvvm-flow:$version"
    }

    object DB {
        const val version = "2.0.0"

        const val android = "app.cash.sqldelight:android-driver:$version"
        const val ios = "app.cash.sqldelight:native-driver:$version"
    }

    object DI {
        private const val version = "3.1.5"

        const val compose = "io.insert-koin:koin-androidx-compose:$version"
        const val core = "io.insert-koin:koin-core:$version"
    }

    object Logger {
        private const val version = "2.6.1"

        const val core = "io.github.aakira:napier:$version"
    }
}