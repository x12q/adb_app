package com.x12q.mocker123._2_service.system_service.command_runner

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.runBlocking
import java.io.File
import com.x12q.common_di.di.global.GlobalComponent
import java.io.IOException
import java.util.concurrent.TimeUnit
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.io.path.Path

@Inject
@ContributesBinding(GlobalComponent.Scope::class)
@SingleIn(GlobalComponent.Scope::class)
class CommandRunnerImp : CommandRunner {

    override suspend fun run(commandInput: CommandInput): Result<CommandOutput, CommandRunnerError> {
        val commandStr: String = commandInput.command
        val workingDir: File = commandInput.workingDirAsFile
        val commandElements = commandStr.split(" ")

        val process = try {
            ProcessBuilder(commandElements)
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
        } catch (exception: Exception) {
            val err = when (exception) {
                is NullPointerException -> CommandRunnerError.CommandElementIsNull(commandInput)
                is IndexOutOfBoundsException -> CommandRunnerError.CommandIsEmpty(commandInput)
                is SecurityException -> CommandRunnerError.SecurityError(commandInput, exception)
                is UnsupportedOperationException -> CommandRunnerError.OSNotAllowCommand(commandInput)
                is IOException -> CommandRunnerError.CommandDoesNotExist(commandInput, exception)
                else -> CommandRunnerError.OtherException(commandInput, exception)
            }
            return Err(err)
        }

        try {
            val futureProcess = process.onExit().get(60, TimeUnit.MINUTES)
            val outputText = futureProcess.inputStream.bufferedReader().readText()
            return Ok(CommandOutput(rawOutput = outputText, exitCode = futureProcess.exitValue()))
        } catch (exception: Exception) {
            val err = when (exception) {
                is InterruptedException -> CommandRunnerError.CommandWasInterrupted(commandInput)
                else -> CommandRunnerError.OtherException(commandInput, exception)
            }
            return Err(err)
        }
    }
}

private fun main() {
    runBlocking {
        println(CommandRunnerImp().run(CommandInput("ls", Path("/"))))
    }
}
