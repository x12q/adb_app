package com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_execution_section

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.run_adb
import com.x12q.adb_app.generated.resources.run_adb_btn
import com.x12q.adb_app.generated.resources.generated_adb_command
import com.x12q.adb_app.generated.resources.run_adb_section
import androidx.compose.material3.Text
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.x12q.common_di.di.viewmodel_di.getVM
import com.x12q.common_ui.preview_views.PreviewBoxOnSurface
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.mocker123.app.main_screen.adb_notification_screen.ADBNotifButton
import com.x12q.mocker123.app.main_screen.adb_notification_screen.SectionBox
import com.x12q.mocker123.app.main_screen.adb_notification_screen.SectionIcon
import com.x12q.mocker123.app.main_screen.adb_notification_screen.SectionTitle
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.AdbCommandState
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.enabledRunButton
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.makeStatusMessage
import com.x12q.mocker123.app.theme.AppTheme
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileId
import org.jetbrains.compose.resources.stringResource

@Composable
fun RunAdbSection(
    adbProfileId: AdbProfileId,
    modifier: Modifier = Modifier,
    viewModel: RunAdbSectionViewModel = getVM<RunAdbSectionViewModel, RunAdbSectionViewModel.Factory>(
        key = "${adbProfileId.uuid}",
        create = { factory -> factory.create(adbProfileId) }
    ),
) {
    val commandState = viewModel.adbCommandStateFlow.collectAsState().value
    RunAdbSection(
        commandText = (commandState as? AdbCommandState.Available)?.adbCommandOutput?.annotatedCommand,
        currentAdbPath = viewModel.adbPathStringFlow.collectAsState().value,
        showPathError = viewModel.pathIsOk.collectAsState().value == false,
        onAdbPathChange = viewModel::onAdbPathChange,
        onCopyClick = viewModel::onCopyClick,
        enableRunButton = commandState.enabledRunButton(),
        onRunClick = viewModel::onRunClick,
        status = commandState,
        modifier = modifier,
    )
}

@Composable
fun RunAdbSection(
    commandText: AnnotatedString?,
    currentAdbPath: String?,
    showPathError: Boolean,
    onAdbPathChange: (String) -> Unit,
    onCopyClick: () -> Unit,
    enableRunButton: Boolean,
    onRunClick: () -> Unit,
    status: AdbCommandState,
    modifier: Modifier = Modifier,
) {
    SectionBox(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                SectionTitle(
                    text = stringResource(Res.string.run_adb_section),
                    icon = { SectionIcon(Res.drawable.run_adb) }
                )
                RunAdbButton(isEnabled = enableRunButton, onClick = onRunClick)
            }
            AdbPathField(
                currentAdbPath = currentAdbPath,
                onAdbPathChange = onAdbPathChange,
                showPathError = showPathError,
            )

            AdbCommandSubtitleTitle(status)
            AdbCommandText(command = commandText, onCopyClick = onCopyClick, modifier = Modifier.weight(0.5f))
        }
    }
}

@Composable
fun AdbCommandSubtitleTitle(status: AdbCommandState, modifier: Modifier = Modifier) {
    val statusStr = status.makeStatusMessage()
    val subtitle = buildAnnotatedString {
        append("${stringResource(Res.string.generated_adb_command)}: ")
        val statusStyle = AppTheme.appStyle.sectionSubTitleSpanStyle.let {
            if (status.isError) {
                it.copy(color = AppTheme.appColor.adbNotificationColor.errorTextColor)
            } else {
                it
            }
        }
        withStyle(statusStyle) {
            append(statusStr)
        }
    }
    Text(
        text = subtitle,
        style = AppTheme.appStyle.sectionSubTitle,
        color = AppTheme.appColor.adbNotificationColor.sectionTitle,
        modifier = modifier,
    )
}

@Composable
private fun RunAdbButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ADBNotifButton(
        text = stringResource(Res.string.run_adb_btn),
        onClick = onClick,
        modifier = modifier,
        isEnabled = isEnabled,
    )
}

@Preview
@Composable
private fun Preview_RunAdbSection() {
    PreviewBoxOnSurface {
        RunAdbSection(
            commandText = AnnotatedString("adb shell am broadcast -a com.example.ACTION --es key value"),
            currentAdbPath = "/usr/local/bin/adb",
            showPathError = false,
            onAdbPathChange = {},
            onCopyClick = {},
            enableRunButton = true,
            onRunClick = {},
            status = AdbCommandState.Loading,
            modifier = Modifier.padding(20.dp),
        )
    }
}

private fun main() {
    previewApp {
        Preview_RunAdbSection()
    }
}
