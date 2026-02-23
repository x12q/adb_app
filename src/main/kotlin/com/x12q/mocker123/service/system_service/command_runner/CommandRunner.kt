package com.x12q.mocker123.service.system_service.command_runner

import com.github.michaelbull.result.Result

/**
 * Run a command-line
 */
interface CommandRunner {
    suspend fun run(commandInput: CommandInput): Result<CommandOutput, CommandRunnerError>

    companion object {
        fun forPreview(): CommandRunner {
            return CommandRunnerImp()
        }
    }
}
