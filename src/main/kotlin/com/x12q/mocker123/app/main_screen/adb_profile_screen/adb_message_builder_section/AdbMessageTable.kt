package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.table_header_key
import com.x12q.adb_app.generated.resources.table_header_type
import com.x12q.adb_app.generated.resources.table_header_value
import com.x12q.adb_app.generated.resources.trash_bin_icon
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.DataEntry
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EiData
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EsData
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import com.x12q.common_ui.button.TransparentIconButton
import com.x12q.common_ui.corner12Border
import com.x12q.common_ui.preview_views.PreviewBoxOnSurface
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.row.CenterAlignRow
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.messages.es.MessageTypeLabel
import com.x12q.mocker123.app.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AdbMessageTable(
    dataEntries: List<DataEntry>,
    onChange: (newEntry: DataEntry) -> Unit,
    onDeleteEntry: (DataEntry) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .corner12Border(color = AppTheme.appColor.adbNotificationColor.sectionBorder)
    ) {
        TableHeaderRow()
        Column(
            Modifier
                .background(AppTheme.appColor.adbNotificationColor.appBackground)
                .verticalScroll(rememberScrollState())

        ) {
            dataEntries.forEachIndexed { index, entry ->
                MessageEntryRow(entry = entry, onChange = onChange, onDeleteClick = { onDeleteEntry(entry) })
                if (index < dataEntries.size) {
                    HorizontalDivider(
                        color = AppTheme.appColor.adbNotificationColor.tableDivider,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


private object TableWeights {
    const val key = 0.3f
    const val value = 1f
    const val type = 0.6f
    const val action = 0.3f
}

@Composable
private fun TableHeaderRow(modifier: Modifier = Modifier) {
    CenterAlignRow(
        modifier
            .background(AppTheme.appColor.adbNotificationColor.tableHeaderBackground)
            .padding(vertical = 4.dp),
    ) {
        TableHeaderCell(
            stringResource(Res.string.table_header_key),
            Modifier.weight(TableWeights.key).padding(start = 10.dp)
        )
        TableHeaderCell(
            stringResource(Res.string.table_header_value),
            Modifier.weight(TableWeights.value).padding(start = 10.dp)
        )
        TableHeaderCell(
            stringResource(Res.string.table_header_type),
            Modifier.weight(TableWeights.type).padding(start = 10.dp)
        )
        TableHeaderCell("", Modifier.weight(TableWeights.action).padding(start = 10.dp))
    }
}

@Composable
private fun MessageEntryRow(
    entry: DataEntry,
    onChange: (newEntry: DataEntry) -> Unit,
    onDeleteClick: () -> Unit,
) {
    when (entry) {
        is EsData -> EsEntryRow(entry = entry, onChange = onChange, onDeleteClick = onDeleteClick)
        is EiData -> EiEntryRow(entry = entry, onChange = onChange, onDeleteClick = onDeleteClick)
    }
}

@Composable
private fun EsEntryRow(
    entry: EsData,
    onChange: (newEntry: EsData) -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp),
    ) {
        TableEditableText(
            text = entry.key ?: "",
            onTextChange = { onChange(entry.copy(key = it)) },
            modifier = Modifier.weight(TableWeights.key).padding(start = 10.dp)
        )
        TableEditableText(
            text = entry.value ?: "",
            onTextChange = { onChange(entry.copy(value = it)) },
            modifier = Modifier.weight(TableWeights.value).padding(start = 10.dp)
        )
        TableContentCell(Modifier.weight(TableWeights.type).padding(start = 10.dp)) {
            MessageTypeLabel(
                type = entry.escapeType,
                fontSize = AppTheme.appStyle.content.fontSize,
                onClick = null
            )
        }
        TableContentCell(Modifier.weight(TableWeights.action).padding(start = 10.dp)) {
            DeleteButton(onDeleteClick)
        }
    }
}

@Composable
private fun EiEntryRow(
    entry: EiData,
    onChange: (newEntry: EiData) -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp),
    ) {
        TableContentText(entry.key ?: "", Modifier.weight(TableWeights.key))
        TableEditableText(
            text = entry.value.toString(),
            onTextChange = { onChange(entry.copy(value = it.toIntOrNull() ?: entry.value)) },
            modifier = Modifier.weight(TableWeights.value)
        )
        TableContentText("ei", Modifier.weight(TableWeights.type))
        TableContentCell(Modifier.weight(TableWeights.action)) {
            DeleteButton(onDeleteClick)
        }
    }
}

@Composable
private fun DeleteButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    TransparentIconButton(
        icon = painterResource(Res.drawable.trash_bin_icon),
        onClick = onClick,
        modifier = modifier,
        tint = AppTheme.appColor.adbNotificationColor.tableIconTint,
    )
}

@Composable
private fun TableHeaderCell(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = AppTheme.appStyle.tableHeader,
            color = AppTheme.appColor.adbNotificationColor.tableHeaderText,
        )
    }
}

@Composable
private fun TableContentText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(start = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = AppTheme.appStyle.content,
            color = AppTheme.appColor.adbNotificationColor.tableContentText,
        )
    }
}

@Composable
private fun TableEditableText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = singleLine,
        textStyle = AppTheme.appStyle.content.copy(
            color = AppTheme.appColor.adbNotificationColor.tableContentText,
        ),
        cursorBrush = SolidColor(AppTheme.appColor.adbNotificationColor.tableContentTextCursor),
        modifier = modifier,
    )
}

@Composable
private fun TableContentCell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.padding(start = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        content()
    }
}

@Preview
@Composable
private fun Preview_AdbMessageTable() {
    PreviewBoxOnSurface {
        AdbMessageTable(
            dataEntries = listOf(
                EsData.random(),
                EsData.random(),
                EiData(id = "ei1", key = "counter", value = 42),
            ),
            onChange = {},
            modifier = Modifier.fillMaxWidth().padding(20.dp),
        )
    }
}

private fun main() {
    previewApp {
        Preview_AdbMessageTable()
    }
}