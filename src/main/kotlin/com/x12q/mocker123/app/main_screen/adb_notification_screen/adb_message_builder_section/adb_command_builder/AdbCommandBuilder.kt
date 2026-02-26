package com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder

import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder.sub_builder.AdbAnnotatedCommandBuilderImp
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder.sub_builder.AdbStrCommandBuilderImp
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.es_formatter.EsFormatterImp
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfile
import java.nio.file.Path


interface AdbCommandBuilder {

    fun build(
        adbProfile: AdbProfile,
        adbPath: Path,
        appPackageNamePlaceholder: String,
    ): AdbCommandBuildOutput

    companion object{
        fun forPreview(): AdbCommandBuilder{
            return AdbCommandBuilderImp(
                strCommandBuilder = AdbStrCommandBuilderImp(
                    esFormatter = EsFormatterImp()
                ),
                annotatedCommandBuilder = AdbAnnotatedCommandBuilderImp(
                    esFormatter = EsFormatterImp()
                ),
            )
        }
    }
}
