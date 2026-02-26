package com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder

import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder.sub_builder.AdbAnnotatedCommandBuilder
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder.sub_builder.AdbStrCommandBuilder
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123.app.di.AppComponent
import java.nio.file.Path
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@ContributesBinding(AppComponent.Scope::class)
@SingleIn(AppComponent.Scope::class)
class AdbCommandBuilderImp(
    val strCommandBuilder: AdbStrCommandBuilder,
    val annotatedCommandBuilder: AdbAnnotatedCommandBuilder,
) : AdbCommandBuilder {

    override fun build(
        adbProfile: AdbProfile,
        adbPath: Path,
        appPackageNamePlaceholder: String
    ): AdbCommandBuildOutput {
        return AdbCommandBuildOutput(
            command = strCommandBuilder.build(
                adbProfile = adbProfile,
                adbPath = adbPath,
                appPackageNamePlaceholder = appPackageNamePlaceholder,
            ),
            annotatedCommand = annotatedCommandBuilder.build(
                adbProfile = adbProfile,
                adbPath = adbPath,
                appPackageNamePlaceholder = appPackageNamePlaceholder,
            ),
        )
    }
}

