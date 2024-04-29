plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    kotlin("plugin.serialization")
    //  id("kotlinx-serialization")
    kotlin("native.cocoapods")
}

group = "com.frpc"
version = "1.0-SNAPSHOT"
val podName = "common"

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    ios()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

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

//    jvm("desktop") {
//        compilations.all {
//            kotlinOptions.jvmTarget = "11"
//        }
//    }
//    js(IR) {
//        browser()
//    }
//    applyDefaultHierarchyTemplate()
    sourceSets {
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
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                api(libs.ktor.ios)
            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

//        val desktopMain by getting {
//            dependencies {
//                api(compose.preview)
//                api(libs.ktor.jvm)
//            }
//        }
//
//        val desktopTest by getting
//
//        val jsMain by getting {
//            dependencies {
//                api(compose.html.core)
//                api(libs.ktor.js)
//                api(libs.ktor.jsonjs)
//            }
//        }
    }
//    explicitApi()
}

android {
    namespace ="com.frpc.common"
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
