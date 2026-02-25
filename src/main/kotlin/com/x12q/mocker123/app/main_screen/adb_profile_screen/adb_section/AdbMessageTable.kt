package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.trash_bin_icon
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.DataEntry
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EiData
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EsData
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
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
import com.x12q.mocker123.app.theme.AppTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun AdbMessageTable(
    dataEntries: List<DataEntry>,
    onDeleteEntry: (DataEntry) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .corner12Border(color = AppTheme.appColor.adbNotificationColor.sectionBorder)
    ) {
        HeaderRow()
        Column(
            Modifier
                .background(AppTheme.appColor.adbNotificationColor.appBackground)
                .verticalScroll(rememberScrollState())

        ) {
            dataEntries.forEachIndexed { index, entry ->
                if (index > 0) {
                    HorizontalDivider(
                        color = AppTheme.appColor.adbNotificationColor.tableDivider,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                MessageEntryRow(entry = entry, onDeleteClick = { onDeleteEntry(entry) })
            }
        }
    }
}

@Composable
private fun HeaderRow(modifier: Modifier = Modifier) {
    CenterAlignRow(
        modifier
            .background(AppTheme.appColor.adbNotificationColor.tableHeaderBackground)
            .padding(vertical = 4.dp),
    ) {
        TableHeaderCell("Key", Modifier.weight(1f))
        TableHeaderCell("Value", Modifier.weight(1f))
        TableHeaderCell("Type", Modifier.weight(0.6f))
        TableHeaderCell("", Modifier.weight(0.3f))
    }
}

@Composable
private fun MessageEntryRow(
    entry: DataEntry,
    onDeleteClick: () -> Unit,
) {
    val key: String
    val value: String
    val type: String

    when (entry) {
        is EsData -> {
            key = entry.key ?: ""
            value = entry.value ?: ""
            type = "es"
        }

        is EiData -> {
            key = entry.key ?: ""
            value = entry.value.toString()
            type = "ei"
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp),
    ) {
        TableContentText(key, Modifier.weight(1f))
        TableContentText(value, Modifier.weight(1f))
        TableContentText(type, Modifier.weight(0.6f))
        TableContentCell(Modifier.weight(0.3f)) {
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
        modifier = modifier.padding(start = 10.dp),
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
            modifier = Modifier.fillMaxWidth().padding(20.dp),
        )
    }
}

private fun main() {
    previewApp {
        Preview_AdbMessageTable()
    }
}