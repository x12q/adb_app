package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.adb_command_builder.AdbCommandBuildOutput
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.adb_command_builder.AdbCommandBuilder
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.add_message_selector.MessageType
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.messages.es.EscapeType
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EsData
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
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.nio.file.InvalidPathException
import java.nio.file.Path
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.UUID
import kotlin.io.path.Path

@Inject
class AdbSectionViewModel(
    @Assisted val adbProfileId: AdbProfileId,
    val container: AdbProfileRepoContainer,
    val adbCommandBuilderProvider: AdbCommandBuilder,
    val commandRunner: CommandRunner,
    val coroutineProvider: GlobalCoroutineProvider,
    val adbPathProvider: AdbPathProvider,
    val clipboardProvider: SystemClipboardProvider,
) {

    private val cr = coroutineProvider.coroutineScope
    private val builder = adbCommandBuilderProvider

    private fun currentProfile(): AdbProfile? = container.profileFlow.value.firstOrNull { it.id == adbProfileId }

    val adbProfileFlow = container.getProfileFlow(adbProfileId.uuid.toString()).filterNotNull()

    val esMapFlow: StateFlow<Map<String, EsData>> = adbProfileFlow
        .map { it.esMap }
        .toStateFlow(cr, emptyMap())

    val adbCommandBuildOutputFlow: Flow<AdbCommandBuildOutput> = combine(
        adbProfileFlow,
        adbPathProvider.adbPathFlow,
    ) { adProfile, adbPath ->
        val commandBuildOutput = builder.build(
            adbProfile = adProfile,
            adbPath = adbPath,
            appPackageNamePlaceholder = "{your.app.package.name}"
        )
        commandBuildOutput
    }

    val adbCommandStateFlow: StateFlow<AdbCommandState> = adbCommandBuildOutputFlow.map { buildOutput ->
        AdbCommandState.Available(buildOutput)
    }.toStateFlow(cr, AdbCommandState.Loading)

    val adbPathStringFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    val pathIsOk: StateFlow<Boolean?> = adbPathStringFlow.map { pathStr->
        if(pathStr.isNullOrEmpty()){
            true
        }else{
            try{
                Path.of(pathStr)
                true
            }catch (e: InvalidPathException){
                false
            }
        }
    }.toStateFlow(cr,null)

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
                        is Err -> {
                            //
                        }

                        is Ok -> {
                            val command = commandRs.value
                            val o = commandRunner.run(CommandInput(command, Path("/")))
                            val profile = currentProfile() ?: return@launch
                            when (o) {
                                is Err -> {
                                    val err = o.error
                                    container.add2(profile.addLog(AdbOutput(err.toString(), Clock.System.now())))
                                }

                                is Ok -> {
                                    val output = o.value
                                    container.add2(profile.addLog(AdbOutput(output.rawOutput, Clock.System.now())))
                                }
                            }
                        }
                    }
                }

                AdbCommandState.Loading -> {
                    // nothing
                }
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

                AdbCommandState.Loading -> {
                    // nothing
                }
            }
        }
    }

    fun onEsChange(newEs: EsData) {
        val profile = currentProfile() ?: return
        container.add2(profile.updateEs(newEs.coerceToEmptyIfNeed()))
    }

    fun addBlankEs() {
        val profile = currentProfile() ?: return
        container.add2(profile.addEs(EsData.empty()))
    }

    fun addTitleEs() {
        val profile = currentProfile() ?: return
        container.add2(profile.addEs(
            EsData.empty().copy(
                key = "title",
                keyIsLocked = true,
                escapeType = EscapeType.PLAIN_TEXT,
            )
        ))
    }

    fun addBodyEs() {
        val profile = currentProfile() ?: return
        container.add2(profile.addEs(
            EsData.empty().copy(
                key = "body",
                keyIsLocked = true,
                escapeType = EscapeType.PLAIN_TEXT,
            )
        ))
    }

    fun onSelectMessageType(messageType: MessageType) {
        when (messageType) {
            MessageType.TITLE -> addTitleEs()
            MessageType.BODY -> addBodyEs()
            MessageType.OTHER -> addBlankEs()
        }
    }

    fun onRemoveEsClick(esData: EsData) {
        val profile = currentProfile() ?: return
        container.add2(profile.removeEs(esData))
    }


    companion object {
        fun forPreview(): AdbSectionViewModel {
            return AdbSectionViewModel(
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


fun main() {
    val q = "a//"
    println(Path.of(q))
}
