plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
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
                implementation(libs.sqldelight.sqlite.driver)
                implementation(libs.mordant)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

sqldelight {
    database("Database") {
        packageName = "org.climatechangemakers.permitdata.database"
        dialect(libs.sqldelight.sqlite.dialect.get())
        deriveSchemaFromMigrations = false
        verifyMigrations = false
    }
}