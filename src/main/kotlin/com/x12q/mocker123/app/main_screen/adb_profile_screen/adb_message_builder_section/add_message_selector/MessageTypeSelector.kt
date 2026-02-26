package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.add_message_selector

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.combinedClickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.json_escape_type_label
import com.x12q.adb_app.generated.resources.message_type_body_label
import com.x12q.adb_app.generated.resources.message_type_title_label
import com.x12q.adb_app.generated.resources.plain_text_escape_type_label
import com.x12q.adb_app.generated.resources.xml_escape_type_label
import com.x12q.common_ui.drop_down_menu.BasicDropdownMenuWithAlwaysClickableItems
import com.x12q.common_ui.preview_views.previewApp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerButtons
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.mouse_right_click_outline
import com.x12q.common_ui.row.CenterAlignRow
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.mocker123.app.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun MessageTypeSelector(
    currentMessageType: MessageType?,
    items: List<MessageType>,
    onSelectType: (MessageType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    CenterAlignRow(modifier = modifier) {

        MessageTypeSelectorButton(
            defaultLabel = "<>",
            currentMessageType = currentMessageType,
            onClick = {
                if (currentMessageType != null) {
                    onSelectType(currentMessageType)
                } else {
                    expanded = true
                }
            },
            onRightClick = {
                if (currentMessageType != null) {
                    expanded = true
                }
            }
        )
        BasicDropdownMenuWithAlwaysClickableItems(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            items = items,
            onPick = { selectedType ->
                onSelectType(selectedType)
                expanded = false
            },
            itemView = { messageType ->
                MessageTypeLabel(label = messageType.getLabel(), fontSize = 14.sp, onClick = null)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageTypeSelectorButton(
    defaultLabel: String,
    currentMessageType: MessageType?,
    onClick: () -> Unit,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TypeLabelBox(
        onClick = onClick, modifier = modifier.onClick(
            enabled = true,
            matcher = PointerMatcher.mouse(PointerButton.Secondary),
            onClick = onRightClick
        )
    ) {
        CenterAlignRow {
            TypeLabelText(
                label = currentMessageType?.getLabel() ?: defaultLabel,
                fontSize = 12.sp,
            )

            if (currentMessageType != null) {
                Icon(
                    painter = painterResource(Res.drawable.mouse_right_click_outline),
                    contentDescription = null,
                    tint = AppTheme.appColor.adbNotificationColor.buttonBackground,
                    modifier = Modifier.size(15.dp).padding(end = 3.dp)
                )
            } else {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = AppTheme.appColor.adbNotificationColor.buttonBackground,
                    modifier = Modifier.size(15.dp).padding(end = 3.dp)
                )
            }
        }
    }
}

@Composable
internal fun MessageType.getLabel(): String {
    return when (this) {
        MessageType.TITLE -> stringResource(Res.string.message_type_title_label)
        MessageType.BODY -> stringResource(Res.string.message_type_body_label)
        MessageType.JSON -> stringResource(Res.string.json_escape_type_label)
        MessageType.XML -> stringResource(Res.string.xml_escape_type_label)
        MessageType.PLAIN_TEXT -> stringResource(Res.string.plain_text_escape_type_label)
    }
}

@Preview
@Composable
private fun Preview_MessageTypeSelector_WithSelection() {
    var type by remember { mutableStateOf<MessageType?>(MessageType.JSON) }
    MessageTypeSelector(
        currentMessageType = type,
        items = MessageType.allEntriesInOrder(),
        onSelectType = { type = it },
    )
}

@Preview
@Composable
private fun Preview_MessageTypeSelector_NoSelection() {
    var type by remember { mutableStateOf<MessageType?>(null) }
    MessageTypeSelector(
        currentMessageType = type,
        items = MessageType.allEntriesInOrder(),
        onSelectType = { type = it },
    )
}

private fun main() {
    previewApp {
        Preview_MessageTypeSelector_NoSelection()
    }
}