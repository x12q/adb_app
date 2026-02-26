package com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.add_message_selector

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.json_escape_type_label
import com.x12q.adb_app.generated.resources.plain_text_escape_type_label
import com.x12q.adb_app.generated.resources.xml_escape_type_label
import com.x12q.common_ui.preview_views.PreviewColumn
import com.x12q.mocker123.app.main_screen.adb_notification_screen.adb_message_builder_section.messages.es.EscapeType
import org.jetbrains.compose.resources.stringResource


@Composable
fun MessageTypeLabel(
    label: String,
    fontSize: TextUnit,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    TypeLabelBox(label = label, fontSize = fontSize, onClick = onClick, modifier = modifier)
}

@Composable
fun MessageTypeLabel(
    escapeType: EscapeType,
    fontSize: TextUnit,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    MessageTypeLabel(
        label = escapeType.getLabel(),
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
            escapeType = EscapeType.JSON,
            fontSize = 10.sp,
            onClick = {},
        )
        MessageTypeLabel(
            escapeType = EscapeType.PLAIN_TEXT,
            fontSize = 10.sp,
            onClick = {},
        )
    }
}
