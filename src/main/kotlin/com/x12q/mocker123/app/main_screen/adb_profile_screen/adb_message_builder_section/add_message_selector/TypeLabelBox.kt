package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.add_message_selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.x12q.common_ui.corner3Border
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.mocker123.app.theme.AppTheme

@Composable
fun TypeLabelBox(
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .corner3Border(1.dp, color = BaseTheme.colors.baseColors.primary)
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            )
    ) {
        content()
    }
}

@Composable
fun TypeLabelText(
    label: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
) {
    ContentText(
        text = label,
        style = BaseTheme.typography.content.copy(
            color = AppTheme.appColor.adbNotificationColor.buttonBackground,
            fontSize = fontSize,
        ),
        modifier = modifier.padding(5.dp),
    )
}

@Composable
fun TypeLabelBox(
    label: String,
    fontSize: TextUnit,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    TypeLabelBox(onClick = onClick, modifier = modifier) {
        TypeLabelText(label = label, fontSize = fontSize)
    }
}