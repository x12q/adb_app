package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.add_message_selector

import androidx.compose.runtime.Composable
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.message_type_body_label
import com.x12q.adb_app.generated.resources.message_type_other_label
import com.x12q.adb_app.generated.resources.message_type_title_label
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MessageType.label(): String {
    return when (this) {
        MessageType.TITLE -> stringResource(Res.string.message_type_title_label)
        MessageType.BODY -> stringResource(Res.string.message_type_body_label)
        MessageType.OTHER -> stringResource(Res.string.message_type_other_label)
    }
}

