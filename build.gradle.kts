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
        commonMain.dependencies {
            implementation(libs.clikt)
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.curl)
            implementation(libs.mordant)
            implementation(libs.sqldelight.sqlite.driver)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("org.climatechangemakers.permitdata.database")
            dialect(libs.sqldelight.sqlite.dialect.get())
            deriveSchemaFromMigrations.set(false)
            verifyMigrations.set(false)
        }
    }
}