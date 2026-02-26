package com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_execution_section

import androidx.lifecycle.ViewModel
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.x12q.common_utils.toStateFlow
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.AdbCommandState
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder.AdbCommandBuildOutput
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.adb_command_builder.AdbCommandBuilder
import com.x12q.mocker123.service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbOutput
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileId
import com.x12q.mocker123.service.local_service.global_coroutine_provider.GlobalCoroutineProvider
import com.x12q.mocker123.service.local_service.global_coroutine_provider.GlobalCoroutineProviderImp
import com.x12q.mocker123.service.system_service.adb_path_manager.AdbPathProvider
import com.x12q.mocker123.service.system_service.command_runner.CommandInput
import com.x12q.mocker123.service.system_service.command_runner.CommandRunner
import com.x12q.mocker123.service.system_service.system_clipboard.SystemClipboardProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import com.x12q.common_di.di.viewmodel_di.ViewModelAssistedFactory
import com.x12q.mocker123.app.main_screen.di.MainScreenSubComponent
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.util.UUID
import kotlin.io.path.Path

@Inject
open class RunAdbSectionViewModel(
    @Assisted val adbProfileId: AdbProfileId,
    val container: AdbProfileRepoContainer,
    val adbCommandBuilderProvider: AdbCommandBuilder,
    val commandRunner: CommandRunner,
    val coroutineProvider: GlobalCoroutineProvider,
    val adbPathProvider: AdbPathProvider,
    val clipboardProvider: SystemClipboardProvider,
) : ViewModel(){
    protected val cr = coroutineProvider.coroutineScope
    private val builder = adbCommandBuilderProvider

    protected fun currentProfile(): AdbProfile? =
        container.profileFlow.value.firstOrNull { it.id == adbProfileId }

    val adbProfileFlow = container.getProfileFlow(adbProfileId.uuid.toString()).filterNotNull()

    val adbCommandBuildOutputFlow: Flow<AdbCommandBuildOutput> = combine(
        adbProfileFlow,
        adbPathProvider.adbPathFlow,
    ) { adProfile, adbPath ->
        builder.build(
            adbProfile = adProfile,
            adbPath = adbPath,
            appPackageNamePlaceholder = "{your.app.package.name}"
        )
    }

    val adbCommandStateFlow: StateFlow<AdbCommandState> = adbCommandBuildOutputFlow.map { buildOutput ->
        AdbCommandState.Available(buildOutput)
    }.toStateFlow(cr, AdbCommandState.Loading)

    val adbPathStringFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    val pathIsOk: StateFlow<Boolean?> = adbPathStringFlow.map { pathStr ->
        if (pathStr.isNullOrEmpty()) {
            true
        } else {
            try {
                Path.of(pathStr)
                true
            } catch (e: InvalidPathException) {
                false
            }
        }
    }.toStateFlow(cr, null)

    fun onAdbPathChange(newPath: String) {
        if (newPath.isEmpty()) {
            adbPathStringFlow.value = null
            adbPathProvider.removeCustomAdbPath()
        } else {
            adbPathStringFlow.value = newPath
            try {
                adbPathProvider.setCustomAdbPath(Path.of(newPath))
            } catch (e: InvalidPathException) {
                adbPathProvider.removeCustomAdbPath()
            }
        }
    }

    fun onRunClick() {
        cr.launch {
            val commandState = adbCommandStateFlow.value
            when (commandState) {
                is AdbCommandState.Available -> {
                    val commandRs = commandState.adbCommandOutput.command
                    when (commandRs) {
                        is Err -> {}
                        is Ok -> {
                            val command = commandRs.value
                            val o = commandRunner.run(CommandInput(command, Path("/")))
                            val profile = currentProfile() ?: return@launch
                            when (o) {
                                is Err -> {
                                    val err = o.error
                                    container.add(profile.addLog(AdbOutput(err.toString(), Clock.System.now())))
                                }
                                is Ok -> {
                                    val output = o.value
                                    container.add(profile.addLog(AdbOutput(output.rawOutput, Clock.System.now())))
                                }
                            }
                        }
                    }
                }
                AdbCommandState.Loading -> {}
            }
        }
    }

    fun onCopyClick() {
        cr.launch {
            val commandState = adbCommandStateFlow.value
            when (commandState) {
                is AdbCommandState.Available -> {
                    val command = commandState.adbCommandOutput.annotatedCommand.toString()
                    clipboardProvider.writeToClipboard(command)
                }
                AdbCommandState.Loading -> {}
            }
        }
    }

    @Inject
    @ContributesBinding(MainScreenSubComponent.Scope::class, multibinding = true)
    class Factory(
        private val factory: (AdbProfileId) -> RunAdbSectionViewModel
    ) : ViewModelAssistedFactory {
        fun create(adbProfileId: AdbProfileId): RunAdbSectionViewModel = factory(adbProfileId)
    }

    companion object {
        fun forPreview(): RunAdbSectionViewModel {
            return RunAdbSectionViewModel(
                adbProfileId = AdbProfileId(UUID.randomUUID()),
                container = AdbProfileRepoContainer.forPreview(),
                adbCommandBuilderProvider = AdbCommandBuilder.forPreview(),
                commandRunner = CommandRunner.forPreview(),
                coroutineProvider = GlobalCoroutineProviderImp(),
                adbPathProvider = AdbPathProvider.forPreview(),
                clipboardProvider = SystemClipboardProvider.forPreview(),
            )
        }
    }
}