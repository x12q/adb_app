
pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
plugins {

    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "adb_app"

include(":x12q_common:common_ui_components")
include(":x12q_common:common_utils")
include(":x12q_common:common_icon")