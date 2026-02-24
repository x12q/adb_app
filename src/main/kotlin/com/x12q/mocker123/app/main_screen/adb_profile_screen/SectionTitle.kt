package com.x12q.mocker123.app.main_screen.adb_profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.target_app
import com.x12q.common_ui.row.CenterAlignRow
import com.x12q.common_ui.spacer.HSpacer
import com.x12q.mocker123.app.theme.AppTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun SectionTitle(
    text: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignRow {
        icon()
        HSpacer(8.dp)
        Text(text = text, style = AppTheme.appStyle.sectionTitle, modifier = modifier)
    }

}