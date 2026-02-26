package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.add_message_selector

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.message_type_body_label
import com.x12q.adb_app.generated.resources.message_type_title_label
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.messages.es.EscapeType
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.messages.es.MessageTypeLabel
import org.jetbrains.compose.resources.stringResource


@Composable
fun AddTitleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MessageTypeLabel(
        label = stringResource(Res.string.message_type_title_label),
        fontSize = 12.sp,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun AddBodyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MessageTypeLabel(
        label = stringResource(Res.string.message_type_body_label),
        fontSize = 12.sp,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun AddJsonButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MessageTypeLabel(
        type = EscapeType.JSON,
        fontSize = 12.sp,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun AddTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MessageTypeLabel(
        type = EscapeType.PLAIN_TEXT,
        fontSize = 12.sp,
        onClick = onClick,
        modifier = modifier,
    )
}