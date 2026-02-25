package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.adb_command_builder

import androidx.compose.ui.text.AnnotatedString
import com.github.michaelbull.result.Result

class AdbCommandBuildOutput(
    /**
     * For execution
     */
    val command: Result<String, AdbCommandBuilderError>,
    /**
     * For presentation + copying to clipboard
     */
    val annotatedCommand: AnnotatedString,
)
