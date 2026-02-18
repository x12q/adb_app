package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._4_adb_output

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.trash_bin_icon
import com.x12q.common_ui.button.IconButton2
import com.x12q.common_ui.theme.BaseTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClearLogButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {


    IconButton2(
        onClick = onClick,
        modifier = modifier,
        colors = BaseTheme.colors.iconButtonColors.copy(
            buttonColors = BaseTheme.colors.buttonColors.copy(
                backgroundColor = BaseTheme.colors.baseColors.surface1,
            )
        ),
        border = BorderStroke(1.dp, SolidColor(BaseTheme.colors.baseColors.faintOnSurface2)),
    ) {
        Icon(
            painter = painterResource(Res.drawable.trash_bin_icon),
            "",
            tint = BaseTheme.colors.baseColors.onPrimary,
        )
    }
}

@Preview
@Composable
private fun Preview_ClearLogButton() {
    ClearLogButton({})
}
