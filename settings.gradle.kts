
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    }
}
rootProject.name = "Lango Coach"
include(":app", ":core:common", ":core:audio", ":core:data", ":core:domain", ":core:ui", ":feature:coach")
