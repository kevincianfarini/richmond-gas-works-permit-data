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

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "org.climatechangemakers.permitdata.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.clikt)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.curl)
                implementation(libs.mordant)
                implementation(libs.sqldelight.sqlite.driver)
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