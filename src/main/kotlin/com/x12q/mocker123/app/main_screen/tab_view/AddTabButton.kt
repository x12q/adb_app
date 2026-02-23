package com.x12q.mocker123.app.main_screen.tab_view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.x12q.common_ui.BasicIcon
import com.x12q.common_ui.button.ButtonColorIndication
import com.x12q.common_ui.theme.BaseTheme

@Composable
internal fun AddTabButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(BaseTheme.shapes.corner6)
            .size(40.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication =ButtonColorIndication.forIconButton(),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
    ) {
        BasicIcon(Icons.Default.Add, "", tint = BaseTheme.colors.baseColors.strongOnSurface1)
    }
}
