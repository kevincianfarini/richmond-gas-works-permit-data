plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

group = "org.climatechangemakers"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    linuxX64 {
        binaries {
            executable {
                entryPoint = "org.climatechangemakers.permitdata.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.curl)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}