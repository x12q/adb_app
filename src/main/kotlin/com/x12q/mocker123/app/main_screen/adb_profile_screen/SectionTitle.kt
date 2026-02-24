package com.x12q.mocker123.app.main_screen.adb_profile_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.x12q.mocker123.app.theme.AppTheme

@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(text = text, style = AppTheme.appStyle.sectionTitle, modifier = modifier)
}