package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.adb_loading
import com.x12q.adb_app.generated.resources.adb_missing_adb
import com.x12q.adb_app.generated.resources.adb_missing_package_name
import com.x12q.adb_app.generated.resources.adb_ok
import com.x12q.mocker123.app.main_screen.adb_profile_screen.SectionBox
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.adb_command_builder.AdbCommandBuilderError
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.add_message_selector.AddMessageSelector
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.add_message_selector.MessageType
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.DataEntry
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EsData
import com.x12q.common_ui.spacer.HSpacer
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.preview_views.PreviewBoxOnSurface
import com.x12q.common_ui.preview_views.PreviewColumn
import com.x12q.common_ui.preview_views.previewApp
import org.jetbrains.compose.resources.stringResource


@Composable
fun AdbSection(
    viewModel: AdbSectionViewModel,
    modifier: Modifier = Modifier,
) {
    val commandState = viewModel.adbCommandStateFlow.collectAsState().value
    val dataEntries: List<DataEntry> = viewModel.esMapFlow.collectAsState().value.values.toList()

    AdbSection(
        status = commandState.makeStatusMessage(),
        dataEntries = dataEntries,
        onChange = viewModel::onEntryChange,
        onAddEsClick = viewModel::addBlankEs,
        onDeleteEntry = viewModel::onRemoveClick,
        onSelectMessageType = viewModel::onSelectMessageType,
        modifier = modifier,
    )
}

@Composable
internal fun AdbSection(
    status: String,
    dataEntries: List<DataEntry>,
    onChange: (DataEntry) -> Unit,
    onAddEsClick: () -> Unit,
    onSelectMessageType: (MessageType) -> Unit,
    onDeleteEntry: (DataEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    SectionBox(modifier){
        Row(Modifier.fillMaxSize()) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                AddMessageSelector(
                    onClick = onAddEsClick,
                    onSelectMessageType = onSelectMessageType,
                )

                ContentText("Status: $status")
            }

            HSpacer(10.dp)

            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                AdbMessageTable(
                    dataEntries = dataEntries,
                    onChange = onChange,
                    onDeleteEntry = onDeleteEntry,
                )
            }
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
                status = "status",
                dataEntries = List(3) { EsData.random() },
                onChange = {},
                onAddEsClick = {},
                onDeleteEntry = {},
                onSelectMessageType = {},
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
