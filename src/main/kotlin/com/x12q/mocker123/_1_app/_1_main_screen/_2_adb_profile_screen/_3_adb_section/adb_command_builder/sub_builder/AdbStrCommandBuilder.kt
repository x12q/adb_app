package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder

import com.github.michaelbull.result.Result
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.AdbCommandBuilderError
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import java.nio.file.Path

interface AdbStrCommandBuilder {
    fun build(
        adbProfile: AdbProfile,
        adbPath: Path,
        appPackageNamePlaceholder: String,
    ): Result<String, AdbCommandBuilderError>
}
