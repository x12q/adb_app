package com.x12q.mocker123.app.title_bar.tab_view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.x12q.common_ui.BasicIcon
import com.x12q.common_ui.button.Button2
import com.x12q.common_ui.button.Button2Colors
import com.x12q.common_ui.button.ButtonColorIndication
import com.x12q.common_ui.theme.BaseTheme


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun CloseTabButton(
    isTabSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button2(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
        colors = Button2Colors.default().copy(
            backgroundColor = if (isTabSelected) {
                BaseTheme.colors.baseColors.faintOnSurface2
            } else {
                BaseTheme.colors.baseColors.surface2
            },
            borderStrokeColor = Color.Transparent
        ),
        indication = ButtonColorIndication.forIconButton(),
        modifier = modifier.padding(end = 5.dp),
        content = {
            BasicIcon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                tint = BaseTheme.colors.baseColors.strongOnSurface1,
                modifier = Modifier.size(17.dp),
            )
        }
    )
}
