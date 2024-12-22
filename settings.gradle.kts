pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://storage.zego.im/maven")
        maven("https://www.jitpack.io")
    }
}

rootProject.name = "chat-app"
include(":app")
include(":core:common")
include(":core:resourse")
include(":core:utils")
include(":data")
include(":domain")
include(":feature:home")
include(":navigation")
include(":feature:auth")
include(":feature:chat")
