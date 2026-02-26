package com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section

import com.github.michaelbull.result.Err
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder.AdbCommandBuildOutput


sealed class AdbCommandState{
    data object Loading: AdbCommandState(){
        override val isError: Boolean = true
    }
    data class Available(
        val adbCommandOutput: AdbCommandBuildOutput
    ): AdbCommandState(){
        override val isError: Boolean = adbCommandOutput.command is Err
    }

    abstract val isError: Boolean
}
