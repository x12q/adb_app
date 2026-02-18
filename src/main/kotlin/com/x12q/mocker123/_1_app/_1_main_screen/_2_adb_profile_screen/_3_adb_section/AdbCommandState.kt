package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.AdbCommandBuildOutput


sealed interface AdbCommandState{
    data object Loading: AdbCommandState
    data class Available(
        val adbCommandOutput: AdbCommandBuildOutput
    ): AdbCommandState
}
