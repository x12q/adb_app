package com.x12q.mocker123.app.main_screen.adb_notification_screen

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.mocker123.app.theme.AppTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.jewel.ui.component.Icon

@Composable
fun SectionIcon(
    drawableResource: DrawableResource,
    modifier: Modifier = Modifier,
) {
    Icon(
        painterResource(drawableResource),
        contentDescription = null,
        tint = AppTheme.appColor.adbNotificationColor.sectionIconTint,
        modifier = modifier.size(20.dp)
    )

}