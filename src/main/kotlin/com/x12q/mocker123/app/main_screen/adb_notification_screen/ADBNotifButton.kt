package com.x12q.mocker123.app.main_screen.adb_notification_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.x12q.common_ui.button.Button2
import com.x12q.mocker123.app.theme.AppTheme

@Composable
fun ADBNotifButton(
    text:String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    Button2(
        onClick = onClick,
        text = text,
        enabled = isEnabled,
        modifier = modifier,
        colors = AppTheme.appColor.adbNotificationColor.button2Color
    )
}