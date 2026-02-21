import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    val kotlinVersion = libs.versions.kotlinVersion.get()
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    alias(libs.plugins.jetbrain.compose)
    alias(libs.plugins.jetbrain.kotlin.plugin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.hot.reload)
}

group = libs.versions.groupId.get()
version = libs.versions.version.get()

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

 kotlin {
     val javaVersion = libs.versions.appJvmVersion.get().toInt()
     jvmToolchain(javaVersion)
     jvmToolchain {
         vendor = JvmVendorSpec.JETBRAINS
     }
 }

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.components.resources)
    implementation(libs.material3)
    implementation("com.x12q:common_ui_components")
    implementation("com.x12q:common_utils")
    implementation(libs.jetbrain.jewel.decorated.window)

    implementation(libs.kotlin.datetime)

    implementation(libs.setting)
    implementation(libs.setting.coroutine)
    implementation(libs.kotlin.coroutine)
    implementation(libs.kotlin.coroutine.test)
    implementation(libs.turbine)

    kapt(libs.dagger.compiler)
    implementation(libs.dagger)
    kaptTest(libs.dagger.compiler)

    implementation(libs.michaelbull.kotlinResult)
    implementation(libs.apache.common.text)
    implementation(libs.kotlin.serialization)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.datetime)
    implementation(libs.jetbrain.runtime)

}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "adb_app"
            packageVersion = libs.versions.appVersion.get()
        }
    }
}
