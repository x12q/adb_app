package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder.AdbAnnotatedCommandBuilder
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder.AdbStrCommandBuilder
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import java.nio.file.Path
import javax.inject.Inject

class AdbCommandBuilderImp @Inject constructor(
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

