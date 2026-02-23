package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.adb_loading
import com.x12q.adb_app.generated.resources.adb_missing_adb
import com.x12q.adb_app.generated.resources.adb_missing_package_name
import com.x12q.adb_app.generated.resources.adb_ok
import com.x12q.adb_app.generated.resources.change_adb_label
import com.x12q.adb_app.generated.resources.change_adb_label_hint
import com.x12q.adb_app.generated.resources.change_adb_path_error
import com.x12q.adb_app.generated.resources.copied
import com.x12q.adb_app.generated.resources.run_adb_btn
import com.x12q.mocker123.app.main_screen.adb_profile_screen.manifest_section.CopyButton
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.adb_command_builder.AdbCommandBuilderError
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.add_message_selector.AddMessageSelector
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.add_message_selector.MessageType
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.messages.es.EsField
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EsData
import com.x12q.common_ui.row.CenterAlignRow
import com.x12q.common_ui.button.Button2
import com.x12q.common_ui.spacer.HSpacer
import com.x12q.common_ui.input_field.InputFieldWithLabel
import com.x12q.common_ui.toast.Toast
import com.x12q.common_ui.toast.ToastDuration
import com.x12q.common_ui.toast.ToastText
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.preview_views.PreviewBoxOnSurface
import com.x12q.common_ui.preview_views.PreviewColumn
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.text.SelectableBoxedText
import com.x12q.common_ui.theme.BaseTheme
import org.jetbrains.compose.resources.stringResource


@Composable
fun AdbSection(
    viewModel: AdbSectionViewModel,
    modifier: Modifier = Modifier,
) {
    val commandState = viewModel.adbCommandStateFlow.collectAsState().value
    val esDataList: List<EsData> = viewModel.esMapFlow.collectAsState().value.values.toList()

    val command = ((commandState as? AdbCommandState.Available)?.adbCommandOutput)?.annotatedCommand
    val status = commandState.makeStatusMessage()
    val enableRunButton = commandState.enabledRunButton()

    AdbSection(
        commandText = command,
        status = status,
        esList = esDataList,
        onEsChange = viewModel::onEsChange,
        enableRunButton = enableRunButton,
        onAddEsClick = viewModel::addBlankEs,
        onRemoveEsClick = viewModel::onRemoveEsClick,
        onRunClick = viewModel::onRunClick,
        onCopyClick = viewModel::onCopyClick,
        onSelectMessageType = viewModel::onSelectMessageType,
        currentAdbPath = viewModel.adbPathStringFlow.collectAsState().value,
        showPathError = viewModel.pathIsOk.collectAsState().value == false,
        onAdbPathChange = viewModel::onAdbPathChange,
        modifier = modifier,
    )
}

@Composable
internal fun AdbSection(
    commandText: AnnotatedString?,
    status: String,
    esList: List<EsData>,
    onEsChange: (newEs: EsData) -> Unit,
    onCopyClick: () -> Unit,
    enableRunButton: Boolean,
    onAddEsClick: () -> Unit,
    onSelectMessageType: (MessageType) -> Unit,
    onRemoveEsClick: (EsData) -> Unit,
    onRunClick: () -> Unit,
    currentAdbPath: String?,
    showPathError: Boolean,
    onAdbPathChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            AddMessageSelector(
                onClick = onAddEsClick,
                onSelectMessageType = onSelectMessageType,
            )

            RunAdbButton(
                isEnabled = enableRunButton,
                onClick = {
                    onRunClick()
                }
            )
            ContentText("Status: $status")
        }

        HSpacer(10.dp)

        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {

            AdbPathField(
                currentAdbPath = currentAdbPath,
                onAdbPathChange = onAdbPathChange,
                showPathError = showPathError,
            )
            AdbCommandTextBox(command = commandText, onCopyClick = onCopyClick, modifier = Modifier.weight(0.5f))

            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.weight(0.5f).verticalScroll(rememberScrollState())
            ) {
                for (es in esList) {
                    EsField(
                        esKey = es.key,
                        esValue = es.value,
                        escapeType = es.escapeType,
                        keyIsLocked = es.keyIsLocked,
                        onKeyChange = { newKey ->
                            val newEs = es.copy(key = newKey)
                            onEsChange(newEs)
                        },
                        onValueChange = { newValue ->
                            val newEs = es.copy(value = newValue)
                            onEsChange(newEs)
                        },
                        onRemoveClick = {
                            onRemoveEsClick(es)
                        },
                        onEscapeTypeChange = { newEscapeType ->
                            val newEs = es.copy(escapeType = newEscapeType)
                            onEsChange(newEs)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AdbPathField(
    currentAdbPath: String?,
    onAdbPathChange: (String) -> Unit,
    showPathError: Boolean,
    modifier: Modifier = Modifier
) {
    CenterAlignRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        InputFieldWithLabel(
            label = stringResource(Res.string.change_adb_label),
            text = currentAdbPath ?: "",
            onTextChange = onAdbPathChange,
            hint = stringResource(Res.string.change_adb_label_hint),
            modifier = Modifier.weight(1f),
            fieldModifier = Modifier.fillMaxWidth(),
            fieldTextStyle = BaseTheme.typography.content.copy(
                color = if (showPathError) {
                    Color.Red
                } else {
                    BaseTheme.colors.baseColors.textOnSurface1
                }
            ),
            singleLine = true,
        )
        if (showPathError) {
            ContentText(
                stringResource(Res.string.change_adb_path_error),
                style = BaseTheme.typography.content.copy(
                    color = Color.Red
                )
            )
        }
    }
}

@Composable
private fun RunAdbButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button2(
        onClick = onClick,
        text = stringResource(Res.string.run_adb_btn),
        enabled = isEnabled,
        modifier = modifier,
    )
}

@Composable
private fun AdbCommandTextBox(
    command: AnnotatedString?,
    onCopyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        SelectableBoxedText(
            text = command ?: AnnotatedString(""),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
        )

        var showToast by remember { mutableStateOf(false) }
        CopyButton(
            onCopyClick = {
                onCopyClick()
                showToast = true
            },
            modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
        )

        Toast(
            initVisibility = showToast,
            duration = ToastDuration.MEDIUM,
            onVisibilityChange = {
                showToast = it
            },
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 15.dp)
        ) {
            ToastText(stringResource(Res.string.copied))
        }
    }

}


@Composable
fun AdbCommandState.makeStatusMessage(): String {
    val r = when (this) {
        is AdbCommandState.Available -> {
            val adCmdOutput = this.adbCommandOutput.command
            when (adCmdOutput) {
                is Err -> {
                    when (adCmdOutput.error) {
                        AdbCommandBuilderError.AdbNotExist -> Res.string.adb_missing_adb
                        AdbCommandBuilderError.MissingAppPackageName -> Res.string.adb_missing_package_name
                    }
                }

                is Ok -> Res.string.adb_ok
            }
        }

        AdbCommandState.Loading -> {
            Res.string.adb_loading
        }
    }
    return stringResource(r)
}

@Preview
@Composable
private fun Preview_AdbSection() {
    PreviewBoxOnSurface {
        PreviewColumn {
            AdbSection(
                commandText = AnnotatedString(text = "adb abc.qwe.123"),
                status = "status",
                enableRunButton = true,
                esList = List(3) {
                    EsData.random()
                },
                onEsChange = {},
                onAddEsClick = {},
                onRemoveEsClick = {},
                onRunClick = {},
                onCopyClick = {},
                onSelectMessageType = {},
                currentAdbPath = "abc",
                showPathError = true,
                onAdbPathChange = {},
            )
        }
    }
}

fun AdbCommandState.enabledRunButton(): Boolean {
    if (this is AdbCommandState.Available) {
        return this.adbCommandOutput.command is Ok
    }
    return false
}

private fun main() {
    previewApp {
        Preview_AdbSection()
    }
}
