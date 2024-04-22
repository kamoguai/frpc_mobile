plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("kotlinx-serialization")
    kotlin("native.cocoapods")
}

group = "com.frpc.common"
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
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js(IR) {
        browser()
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
            baseName = podName
            isStatic = true
        }
    }
    applyDefaultHierarchyTemplate()
    sourceSets {
        val commonMain by getting {
            dependencies {
                //kotlin
                api(libs.kotlinx.serialization)
                api(libs.kotlinx.coroutines)

                //compose
                api(compose.runtime)
                api(compose.ui)
                api(compose.foundation)
                api(compose.materialIconsExtended)
                api(compose.material3)
                api(libs.ktor.core)
                api(libs.koin.core)
                api(libs.koin.jb.compose)

                api(libs.precompose)
                api(libs.precompose.viewmodel)
                api(libs.precompose.koin)
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

        val desktopMain by getting {
            dependencies {
                api(compose.preview)
                api(libs.ktor.jvm)
            }
        }

        val desktopTest by getting

        val jsMain by getting {
            dependencies {
                api(compose.html.core)
                api(libs.ktor.js)
                api(libs.ktor.jsonjs)
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
    }

    explicitApi()
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
