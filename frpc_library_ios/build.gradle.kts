import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
}

group = "com.frpc.frpc.go"
version = "1.0.0"

kotlin {
    applyDefaultHierarchyTemplate()
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.experimental.ExperimentalNativeApi")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlinx.cinterop.BetaInteropApi")
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.compilations.getByName("main") {
            val fileName = when (iosTarget.targetName) {
                "iosArm64" -> "ios-arm64"
                "iosX64", "iosSimulatorArm64" -> "ios-arm64_x86_64-simulator"
                else -> error("Unknown target ${iosTarget.targetName}")
            }
            val xcPath = file("frameworks/Frpclib.xcframework/$fileName/").absolutePath
            println("${iosTarget.targetName} xcPath: $xcPath")

            cinterops.create("Frpclib") {
                defFile("src/iosMain/cinterop/frpc.def")
                compilerOpts("-framework", "Frpclib", "-F$xcPath/") //, "-rpath", frameworksPath
                extraOpts("-compiler-option", "-fmodules")
            }

            iosTarget.binaries.all {
                linkerOpts("-framework", "Frpclib", "-F$xcPath/")
            }
        }
    }
}
