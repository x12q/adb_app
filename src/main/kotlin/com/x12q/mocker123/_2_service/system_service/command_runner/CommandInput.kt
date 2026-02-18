package com.x12q.mocker123._2_service.system_service.command_runner

import java.io.File
import java.nio.file.Path

data class CommandInput(
    val command: String,
    val workingDir: Path,
) {
    val workingDirAsFile: File = workingDir.toFile()
}
