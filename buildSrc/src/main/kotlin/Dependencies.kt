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
}