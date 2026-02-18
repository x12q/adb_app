package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder

import androidx.compose.ui.text.AnnotatedString
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.AnnotatedStrResult
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import java.nio.file.Path

interface AdbAnnotatedCommandBuilder {

    @Deprecated("consider moving this function to view layer because it is containing logic that belong soly to the view layer")
    fun build(
        adbProfile: AdbProfile,
        adbPath: Path,
        appPackageNamePlaceholder: String,
    ): AnnotatedString
}


