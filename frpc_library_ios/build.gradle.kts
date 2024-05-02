import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "com.frpc.frpc.go"
version = "1.0.0"

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    applyDefaultHierarchyTemplate()
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.experimental.ExperimentalNativeApi")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlinx.cinterop.BetaInteropApi")
            }
        }
        androidMain {
            dependencies {

            }
        }
    }
    targets.withType<KotlinNativeTarget> {
        val frameworksPath = file("$projectDir/frameworks")
        compilations.getByName("main") {
            cinterops.create(" ") {
                defFile(" ")
                headers(
                    file("").listFiles().orEmpty()
                )
                compilerOpts("-framework", "Frpclib", "-F$frameworksPath")
            }
        }
        binaries.all {
            linkerOpts("-framework", "Frpclib", "-F$frameworksPath")
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.frpc.frpc.go.library"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
