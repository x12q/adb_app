package com.x12q.mocker123.app.main_screen.tab_view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.x12q.common_ui.theme.BaseTheme


@Composable
fun TabLabelTextField(
    label: String,
    onLabelChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    /* This local text value is here to initialize and manage the text cursor position */
    var localTextValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = label,
                selection = TextRange(label.length)
            )
        )
    }

    BasicTextField(
        value = localTextValue,
        onValueChange = {
            localTextValue = it
            onLabelChange(it.text)
        },
        textStyle = BaseTheme.typography.content,
        modifier = modifier.padding(start = 15.dp),
        cursorBrush = SolidColor(BaseTheme.colors.baseColors.onSurface2),
        singleLine = true,
    )
}
