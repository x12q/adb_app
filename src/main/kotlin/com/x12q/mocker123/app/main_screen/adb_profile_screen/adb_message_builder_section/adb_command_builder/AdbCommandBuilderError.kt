package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.adb_command_builder

sealed interface AdbCommandBuilderError{
    data object MissingAppPackageName:AdbCommandBuilderError
    data object AdbNotExist: AdbCommandBuilderError
}
