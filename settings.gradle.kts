
pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Lango Coach"
include(":app", ":core:common", ":core:audio", ":core:data", ":core:domain", ":core:ui", ":feature:coach", ":core:android")
