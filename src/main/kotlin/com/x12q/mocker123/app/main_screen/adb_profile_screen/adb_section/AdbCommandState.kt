package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section

import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.adb_command_builder.AdbCommandBuildOutput


sealed interface AdbCommandState{
    data object Loading: AdbCommandState
    data class Available(
        val adbCommandOutput: AdbCommandBuildOutput
    ): AdbCommandState
}
