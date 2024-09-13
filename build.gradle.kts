// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add the Google services classpath with a recent version
        classpath("com.google.gms:google-services:4.3.14") // Match the version used in settings.gradle.kts
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
