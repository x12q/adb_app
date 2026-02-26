package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.messages.es

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.json_escape_type_label
import com.x12q.adb_app.generated.resources.plain_text_escape_type_label
import com.x12q.adb_app.generated.resources.xml_escape_type_label
import com.x12q.common_ui.corner3Border
import com.x12q.common_ui.preview_views.PreviewColumn
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.theme.BaseTheme
import org.jetbrains.compose.resources.stringResource


@Composable
fun MessageTypeLabel(
    label: String,
    fontSize: TextUnit,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .corner3Border(1.dp, color = BaseTheme.colors.baseColors.primary)
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            )
    ) {
        ContentText(
            text = label,
            style = BaseTheme.typography.content.copy(
                color = BaseTheme.colors.baseColors.primary,
                fontSize = fontSize,
            ),
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Composable
fun MessageTypeLabel(
    type: EscapeType,
    fontSize: TextUnit,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    MessageTypeLabel(
        label = type.getLabel(),
        fontSize = fontSize,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
internal fun EscapeType.getLabel(): String {
    return when (this) {
        EscapeType.JSON -> stringResource(Res.string.json_escape_type_label)
        EscapeType.PLAIN_TEXT -> stringResource(Res.string.plain_text_escape_type_label)
        EscapeType.XML -> stringResource(Res.string.xml_escape_type_label)
    }
}


@Preview
@Composable
private fun Preview_TypeLabel() {
    PreviewColumn {
        MessageTypeLabel(
            type = EscapeType.JSON,
            fontSize = 10.sp,
            onClick = {},
        )
        MessageTypeLabel(
            type = EscapeType.PLAIN_TEXT,
            fontSize = 10.sp,
            onClick = {},
        )
    }
}
