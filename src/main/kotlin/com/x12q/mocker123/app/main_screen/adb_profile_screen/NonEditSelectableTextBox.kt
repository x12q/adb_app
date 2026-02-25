package com.x12q.mocker123.app.main_screen.adb_profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.x12q.common_ui.corner6Border
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.mocker123.app.theme.AppTheme
import org.jetbrains.jewel.ui.component.Text


@Composable
fun NonEditSelectableTextBox(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .corner6Border(color = AppTheme.appColor.adbNotificationColor.nonEditTextBoxBackground)
            .background(color = AppTheme.appColor.adbNotificationColor.nonEditTextBoxBackground)
            .padding(horizontal = 8.dp, vertical = 7.dp)
    ) {
        SelectionContainer {
            Text(text, style = BaseTheme.typography.content)
        }
    }
}