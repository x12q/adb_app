package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.add_message_selector

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.json_escape_type_label
import com.x12q.adb_app.generated.resources.message_type_body_label
import com.x12q.adb_app.generated.resources.message_type_text_label
import com.x12q.adb_app.generated.resources.message_type_title_label
import com.x12q.mocker123.app.main_screen.adb_profile_screen.ADBNotifButton
import org.jetbrains.compose.resources.stringResource


@Composable
fun AddTitleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ADBNotifButton(
        stringResource(Res.string.message_type_title_label),
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun AddBodyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ADBNotifButton(
        stringResource(Res.string.message_type_body_label),
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun AddJsonButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ADBNotifButton(
        stringResource(Res.string.json_escape_type_label),
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun AddTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ADBNotifButton(
        stringResource(Res.string.message_type_text_label),
        onClick = onClick,
        modifier = modifier,
    )
}