
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "Lango Coach"
include(":app", ":core:common", ":core:audio", ":core:data", ":core:domain", ":core:ui", ":feature:coach")
