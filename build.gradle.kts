plugins {
    alias(libs.plugins.kotlin.multiplatform)
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

            }
        }
    }
}