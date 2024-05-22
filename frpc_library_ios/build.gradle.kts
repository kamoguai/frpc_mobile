import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
}

group = "com.frpc.frpc.go"
version = "1.0.0"

kotlin {
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
    }
    targets.withType<KotlinNativeTarget> {
        val frameworksPath = file("$projectDir/extFramework")
        compilations.getByName("main") {
            cinterops.create("riskperception") {
                defFile("src/iosMain/cinterop/riskperception.def")
                headers(
                    file("${frameworksPath}/Frpclib.framework/Headers").listFiles().orEmpty()
                )
                compilerOpts("-framework", "RiskPerception", "-F$frameworksPath")
            }
        }
        binaries.all {
            linkerOpts("-framework", "Frpclib", "-F$frameworksPath")
        }
    }
}
