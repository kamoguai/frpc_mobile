plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
    alias(libs.plugins.jetbrains.compose.compiler)
}

group = "com.frpc"
version = "1.0-SNAPSHOT"

android {
    compileSdk = 34
    namespace="com.frpc.demo"
    buildFeatures {
        compose = true
    }
    defaultConfig {
        applicationId = "com.frpc.demo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
}

dependencies {
    implementation(project(":common"))
//    implementation(projects.common)
    implementation(libs.androidx.activity.compose)
}

tasks.register("BuildAndRun") {
    doFirst {
        exec {
            workingDir(projectDir.parentFile)
            commandLine("./gradlew", "android:build")
            commandLine("./gradlew", "android:installDebug")
        }
    }
}