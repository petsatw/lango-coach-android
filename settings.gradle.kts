pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0"
        id("org.jetbrains.kotlin.plugin.compose") version "2.2.0"
    }
}
rootProject.name = "Lango Coach"
include(":app", ":core:common", ":core:audio", ":core:data", ":core:domain", ":core:ui", ":feature:coach")