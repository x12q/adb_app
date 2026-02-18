package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._2_manifest_section

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.check_icon
import com.x12q.adb_app.generated.resources.copy_icon
import com.x12q.common_ui.button.IconButton2
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.common_ui.preview_views.PreviewRow
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun CopyButton(
    onCopyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showCopyIcon by remember { mutableStateOf(true) }
    var currentIconJob: Job? = remember { null }
    val cr = rememberCoroutineScope()

    CopyButton(
        showCopyIcon = showCopyIcon,
        onCopyClick = {
            cr.launch {
                onCopyClick()
            }
            if(currentIconJob==null){
                currentIconJob = cr.launch {
                    showCopyIcon = false
                    delay(5000)
                    showCopyIcon = true
                    currentIconJob?.cancel()
                    currentIconJob = null
                }
            }
        },
        modifier = modifier
    )
}


@Composable
internal fun CopyButton(
    showCopyIcon: Boolean,
    onCopyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton2(
        onClick = onCopyClick,
        modifier = modifier,
        colors = BaseTheme.colors.iconButtonColors.copy(
            buttonColors = BaseTheme.colors.buttonColors.copy(
                backgroundColor = BaseTheme.colors.baseColors.surface1,
            )
        ),
        border = BorderStroke(1.dp, SolidColor(BaseTheme.colors.baseColors.faintOnSurface2)),
    ) {
        val iconRes = if (showCopyIcon) {
            Res.drawable.copy_icon
        } else {
            Res.drawable.check_icon
        }
        Icon(
            painter = painterResource(iconRes),
            "",
            tint = BaseTheme.colors.baseColors.onPrimary,
        )
    }
}

@Preview
@Composable
private fun Preview_CopyButton() {
    PreviewRow {
        CopyButton(true, {})
        CopyButton(false, {})
    }
}
