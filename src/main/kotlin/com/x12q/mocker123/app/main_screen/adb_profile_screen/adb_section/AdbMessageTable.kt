package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.trash_bin_icon
import com.x12q.common_ui.button.IconButton2
import com.x12q.common_ui.text.LabelText
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.DataEntry
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EiData
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EsData
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.x12q.common_ui.corner12Border
import com.x12q.common_ui.corner6Border
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
                DataRow(entry = entry, onDeleteClick = { onDeleteEntry(entry) })
            }
        }
    }
}

@Composable
private fun HeaderRow(modifier: Modifier = Modifier) {
    CenterAlignRow(
        modifier
            .background(AppTheme.appColor.adbNotificationColor.tableHeaderBackground)
            .padding(vertical = 4.dp)
        ,
    ) {
        Cell(Modifier.weight(1f)) { TableHeaderText("Key") }
        Cell(Modifier.weight(1f)) { TableHeaderText("Value") }
        Cell(Modifier.weight(0.6f)) { TableHeaderText("Type") }
        Cell(Modifier.weight(0.3f)) {}
    }
}

@Composable
private fun TableHeaderText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = AppTheme.baseTheme.typography.content,
        color = AppTheme.appColor.adbNotificationColor.tableHeaderText,
        modifier = modifier
    )
}

@Composable
private fun DataRow(
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
        Cell(Modifier.weight(1f)) { LabelText(key) }
        Cell(Modifier.weight(1f)) { LabelText(value) }
        Cell(Modifier.weight(0.6f)) { LabelText(type) }
        Cell(Modifier.weight(0.3f)) {
            IconButton2(
                painter = painterResource(Res.drawable.trash_bin_icon),
                onClick = onDeleteClick,
            )
        }
    }
}

@Composable
private fun Cell(
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