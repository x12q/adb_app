package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.add_message_selector

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.x12q.common_ui.drop_down_menu.DropDownMenuItem2
import com.x12q.common_ui.drop_down_menu.DropdownMenu2

@Composable
internal fun MessageSelectorMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<MessageType>,
    onSelectMessageType: (MessageType) -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu2(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        items = items,
        modifier = modifier,
        itemView = { messageType ->
            DropDownMenuItem2(
                label = messageType.label(),
                onClick = {
                    onSelectMessageType(messageType)
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

