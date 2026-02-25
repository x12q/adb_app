package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_execution_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.change_adb_label
import com.x12q.adb_app.generated.resources.change_adb_label_hint
import com.x12q.adb_app.generated.resources.change_adb_path_error
import com.x12q.common_ui.input_field.InputFieldWithLabel
import com.x12q.common_ui.row.CenterAlignRow
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.theme.BaseTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun AdbPathField(
    currentAdbPath: String?,
    onAdbPathChange: (String) -> Unit,
    showPathError: Boolean,
    modifier: Modifier = Modifier
) {
    CenterAlignRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        InputFieldWithLabel(
            label = stringResource(Res.string.change_adb_label),
            text = currentAdbPath ?: "",
            onTextChange = onAdbPathChange,
            hint = stringResource(Res.string.change_adb_label_hint),
            modifier = Modifier.weight(1f),
            fieldModifier = Modifier.fillMaxWidth(),
            fieldTextStyle = BaseTheme.typography.content.copy(
                color = if (showPathError) {
                    Color.Red
                } else {
                    BaseTheme.colors.baseColors.textOnSurface1
                }
            ),
            singleLine = true,
        )
        if (showPathError) {
            ContentText(
                stringResource(Res.string.change_adb_path_error),
                style = BaseTheme.typography.content.copy(
                    color = Color.Red
                )
            )
        }
    }
}
