package com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder.sub_builder

import androidx.compose.ui.text.AnnotatedString
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfile
import java.nio.file.Path

interface AdbAnnotatedCommandBuilder {

    @Deprecated("consider moving this function to view layer because it is containing logic that belong soly to the view layer")
    fun build(
        adbProfile: AdbProfile,
        adbPath: Path,
        appPackageNamePlaceholder: String,
    ): AnnotatedString
}


