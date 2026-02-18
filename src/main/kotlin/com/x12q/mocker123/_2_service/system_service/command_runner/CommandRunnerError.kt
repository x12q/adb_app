package com.x12q.mocker123._2_service.system_service.command_runner

import java.io.IOException

sealed interface CommandRunnerError {

    data class CommandElementIsNull(val commandInput: CommandInput) : CommandRunnerError

    data class OtherException(
        val commandInput: CommandInput,
        val exception: Exception,
    ) : CommandRunnerError

    data class CommandIsEmpty(val commandInput: CommandInput) : CommandRunnerError
    data class SecurityError(
        val commandInput: CommandInput,
        val exception: SecurityException,
    ) : CommandRunnerError

    data class OSNotAllowCommand(val commandInput: CommandInput) : CommandRunnerError

    data class CommandDoesNotExist(
        val commandInput: CommandInput,
        val exception: IOException,
    ) : CommandRunnerError

    data class CommandWasInterrupted(val value: CommandInput) : CommandRunnerError
}
