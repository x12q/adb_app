package com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.add_message_selector

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.x12q.common_ui.row.CenterAlignRow
import com.x12q.common_ui.drop_down_menu.BasicDropdownMenuWithAlwaysClickableItems
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.messages.es.EscapeType

@Composable
fun EscapeTypeSelector(
    currentEscapeType: EscapeType,
    onSelectType: (EscapeType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val items = remember { EscapeType.entries.toList() }

    CenterAlignRow(modifier = modifier) {
        MessageTypeLabel(
            escapeType = currentEscapeType,
            fontSize = 10.sp,
            onClick = { expanded = true },
        )
        BasicDropdownMenuWithAlwaysClickableItems(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            items = items,
            onPick = { selectedType ->
                onSelectType(selectedType)
                expanded = false
            },
            itemView = { escapeType ->
                MessageTypeLabel(escapeType = escapeType, fontSize = 14.sp, onClick = null)
            }
        )
    }
}

@Preview
@Composable
private fun Preview_EscapeTypeSelector() {
    var type by remember { mutableStateOf(EscapeType.JSON) }
    EscapeTypeSelector(
        currentEscapeType = type,
        onSelectType = { type = it },
    )
}

private fun main() {
    previewApp {
        Preview_EscapeTypeSelector()
    }
}
