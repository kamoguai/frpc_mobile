import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    alias(libs.plugins.jetbrains.compose.compiler)
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    //  id("kotlinx-serialization")
    kotlin("native.cocoapods")
//    id("org.jetbrains.kotlin.plugin.atomicfu")
}

group = "com.frpc"
version = "1.0-SNAPSHOT"
val podName = "common"

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
kotlin {
    androidTarget()
    jvm("desktop")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "common"
            // isStatic = true
        }
        iosTarget.compilations.getByName("main") {
            val fileName = when (iosTarget.targetName) {
                "iosArm64" -> "ios-arm64"
                "iosX64", "iosSimulatorArm64" -> "ios-arm64_x86_64-simulator"
                else -> error("Unknown target ${iosTarget.targetName}")
            }
            val xcPath = file("${rootDir}/frpc_library/frameworks/Frpclib.xcframework/$fileName/").absolutePath
            println("${iosTarget.targetName} xcPath: $xcPath")

            cinterops.create("Frpclib") {
                defFile("${rootDir}/frpc_library/src/iosMain/cinterop/frpc.def")
                compilerOpts("-framework", "Frpclib", "-F$xcPath/") //, "-rpath", frameworksPath
                extraOpts("-compiler-option", "-fmodules")
            }

            iosTarget.binaries.all {
                linkerOpts("-framework", "Frpclib", "-F$xcPath/")
            }
        }
    }
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("jvmCommon") {
                withAndroidTarget()
                withJvm()
            }
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "common"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.experimental.ExperimentalNativeApi")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlinx.cinterop.BetaInteropApi")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.serialization.json)

                api(compose.runtime)
                api(compose.ui)
                api(compose.foundation)
                implementation(compose.components.resources)
                api(compose.materialIconsExtended)
                api(compose.material3)
                api(libs.ktor.core)
                api(libs.koin.core)

                implementation(libs.precompose)
                implementation(libs.precompose.molecule)
                implementation(libs.precompose.viewmodel)
                implementation(libs.molecule.runtime)

                implementation(libs.settings)

                api(libs.compose.webview.multiplatform)
                api("io.github.kevinnzou:compose-webview-multiplatform:1.9.4")
//                implementation("co.touchlab:kermit:2.0.3")
//                implementation("org.jetbrains.kotlinx:atomicfu:0.23.2")

                implementation(compose.components.resources)
                ///region 讀寫檔案
                implementation("io.github.skolson:kmp-io:0.1.5")
                ///endregion

            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.androidx.appcompat)
                api(libs.androidx.core.ktx)
                api(libs.androidx.activity.compose)
                implementation(libs.ktor.android)
                api(libs.androidx.lifecycle.runtime.ktx)
                compileOnly(libs.androidx.ui.tooling.preview)
                api(libs.ktor.jvm)
                implementation(project(":frpc_library"))
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                api(libs.ktor.ios)
                // implementation(project(":frpc_library_ios"))
            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val desktopMain by getting {
            dependencies {
                api(compose.preview)
                implementation(compose.desktop.common)
                api(libs.ktor.jvm)
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutinesVersion")
            }
        }
    }
    jvmToolchain(11)
}

android {
    namespace ="com.frpc.common"
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    sourceSets["main"].res.srcDirs("src/androidMain/res")
//    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
   // packaging {
   //     resources {
   //         excludes += "/META-INF/{AL2.0,LGPL2.1}"
   //     }
   // }
}
