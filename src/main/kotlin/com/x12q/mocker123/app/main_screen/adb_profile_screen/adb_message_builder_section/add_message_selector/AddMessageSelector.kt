package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.add_message_selector

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.add_message_btn
import com.x12q.adb_app.generated.resources.more_button_icon
import com.x12q.common_ui.button.Button2
import com.x12q.common_ui.cornerXBorder
import com.x12q.common_ui.preview_views.PreviewBoxOnSurface
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.text.ButtonText
import com.x12q.common_ui.theme.BaseTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun AddMessageSelector(
    onClick: () -> Unit,
    onSelectMessageType: (MessageType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .width(120.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AddMessageButton(onClick = onClick, modifier = Modifier.weight(1f))

        VerticalLine()

        MessageSelectorButton(onSelectMessageType, Modifier.fillMaxHeight())
    }
}

@Composable
private fun VerticalLine() {
    Spacer(Modifier.fillMaxHeight().width(1.dp).background(BaseTheme.colors.baseColors.onPrimary))
}

@Composable
private fun AddMessageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .cornerXBorder(
                width = 1.dp,
                color = BaseTheme.colors.baseColors.primary,
                shape = BaseTheme.shapes.corner6.copy(
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp),
                )
            ).fillMaxHeight()
            .background(BaseTheme.colors.baseColors.primary)
            .clickable {
                onClick()
            }
    ) {
        ButtonText(
            stringResource(Res.string.add_message_btn), modifier = Modifier
                .padding(vertical = 5.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
internal fun MessageSelectorButton(
    onMessageTypeSelect: (MessageType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Box(
            modifier.cornerXBorder(
                width = 1.dp,
                color = BaseTheme.colors.baseColors.primary,
                shape = BaseTheme.shapes.corner6.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                )
            ).fillMaxHeight()
                .width(31.dp)
                .background(BaseTheme.colors.baseColors.primary)
                .clickable {
                    expanded = true
                }
        ) {
            Icon(
                painter = painterResource(Res.drawable.more_button_icon),
                "",
                modifier = Modifier.align(Alignment.Center),
                tint = BaseTheme.colors.baseColors.onPrimary
            )
        }

        MessageSelectorMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            items = remember { MessageType.allEntriesInOrder() },
            onSelectMessageType = {
                expanded = false
                onMessageTypeSelect(it)
            },
        )
    }
}


@Preview
@Composable
private fun Preview_AddMessageButton() {
    PreviewBoxOnSurface() {
        AddMessageSelector(
            onClick = {},
            onSelectMessageType = {},
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private fun main() {
    previewApp {
        Preview_AddMessageButton()
    }
}

