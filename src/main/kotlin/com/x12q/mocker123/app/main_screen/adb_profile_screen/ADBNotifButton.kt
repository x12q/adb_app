package com.x12q.mocker123.app.main_screen.adb_profile_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.run_adb_btn
import com.x12q.common_ui.button.Button2
import com.x12q.mocker123.app.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

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