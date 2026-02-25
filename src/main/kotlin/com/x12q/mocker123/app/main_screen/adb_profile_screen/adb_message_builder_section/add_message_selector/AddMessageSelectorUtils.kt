package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.add_message_selector

import androidx.compose.runtime.Composable
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.json_escape_type_label
import com.x12q.adb_app.generated.resources.message_type_body_label
import com.x12q.adb_app.generated.resources.message_type_title_label
import com.x12q.adb_app.generated.resources.plain_text_escape_type_label
import com.x12q.adb_app.generated.resources.xml_escape_type_label
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MessageType.label(): String {
    return when (this) {
        MessageType.TITLE -> stringResource(Res.string.message_type_title_label)
        MessageType.BODY -> stringResource(Res.string.message_type_body_label)
        MessageType.JSON -> stringResource(Res.string.json_escape_type_label)
        MessageType.XML -> stringResource(Res.string.xml_escape_type_label)
        MessageType.PLAIN_TEXT -> stringResource(Res.string.plain_text_escape_type_label)
    }
}

