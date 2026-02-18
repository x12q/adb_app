package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder

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
