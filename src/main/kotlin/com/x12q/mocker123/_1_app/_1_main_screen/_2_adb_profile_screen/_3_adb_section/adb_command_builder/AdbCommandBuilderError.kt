package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder

sealed interface AdbCommandBuilderError{
    data object MissingAppPackageName:AdbCommandBuilderError
    data object AdbNotExist: AdbCommandBuilderError
}
