package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_execution_section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.copied
import com.x12q.common_ui.text.SelectableBoxedText
import com.x12q.common_ui.toast.Toast
import com.x12q.common_ui.toast.ToastDuration
import com.x12q.common_ui.toast.ToastText
import com.x12q.mocker123.app.main_screen.adb_profile_screen.NonEditSelectableTextBox
import com.x12q.mocker123.app.main_screen.adb_profile_screen.manifest_section.CopyButton
import com.x12q.mocker123.app.main_screen.adb_profile_screen.manifest_section.ManifestTextBox
import org.jetbrains.compose.resources.stringResource


@Composable
fun AdbCommandTextBox(
    command: AnnotatedString?,
    onCopyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        NonEditSelectableTextBox(
            text = command ?: AnnotatedString(""),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
        )

        var showToast by remember { mutableStateOf(false) }
        CopyButton(
            onCopyClick = {
                onCopyClick()
                showToast = true
            },
            modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
        )

        Toast(
            initVisibility = showToast,
            duration = ToastDuration.MEDIUM,
            onVisibilityChange = {
                showToast = it
            },
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 15.dp)
        ) {
            ToastText(stringResource(Res.string.copied))
        }
    }

}
