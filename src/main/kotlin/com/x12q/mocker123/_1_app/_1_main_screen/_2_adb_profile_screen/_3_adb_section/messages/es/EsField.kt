package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.messages.es

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.es_key_hint
import com.x12q.adb_app.generated.resources.es_value_hint
import com.x12q.adb_app.generated.resources.trash_bin_icon
import com.x12q.common_ui.box.BasicContentBox
import com.x12q.common_ui.button.IconButton2
import com.x12q.common_ui.input_field.BoxedTextField
import com.x12q.common_ui.CenterAlignRow
import com.x12q.common_ui.spacer.HSpacer
import com.x12q.common_ui.corner4Border
import com.x12q.common_ui.preview_views.PreviewBoxOnSurface
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.text.LabelText
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EsField(
    esKey: String?,
    esValue: String?,
    keyIsLocked: Boolean,
    escapeType: EscapeType,
    onKeyChange: (newKey: String) -> Unit,
    onValueChange: (newValue: String) -> Unit,
    onEscapeTypeChange: (EscapeType) -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    CenterAlignRow(modifier = modifier) {
        LabelText("Text(--es)")

        HSpacer(15.dp)
        BoxedTextField(
            text = esKey ?: "",
            hint = stringResource(Res.string.es_key_hint),
            onTextChange = onKeyChange,
            readOnly = keyIsLocked
        )

        HSpacer(8.dp)

        ValueFieldWithSelector(
            text = esValue ?: "",
            onValueChange = onValueChange,
            escapeType = escapeType,
            onEscapeTypeChange = onEscapeTypeChange,
            modifier = Modifier.weight(1f)
        )
        HSpacer(8.dp)
        IconButton2(painter = painterResource(Res.drawable.trash_bin_icon), onClick = onRemoveClick)

    }
}

@Composable
private fun ValueFieldWithSelector(
    text: String,
    onValueChange: (newValue: String) -> Unit,
    escapeType: EscapeType,
    onEscapeTypeChange: (EscapeType) -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignRow(
        modifier = modifier.corner4Border().padding(start = 4.dp),
    ) {
        EscapeTypeSelector(
            currentEscapeType = escapeType,
            onSelectType = onEscapeTypeChange,
        )

        BoxedTextField(
            text = text,
            hint = stringResource(Res.string.es_value_hint),
            singleLine = false,
            onTextChange = onValueChange,
            modifier = Modifier.weight(1f),
            containerBox = { mod,content ->
                BasicContentBox(mod) {
                    content()
                }
            }
        )

    }
}


@Preview
@Composable
private fun Preview_EsField() {
    PreviewBoxOnSurface {
        EsField(
            esKey = "esKey",
            esValue = "some mess",
            keyIsLocked = false,
            escapeType = EscapeType.JSON,
            onKeyChange = {},
            onValueChange = {},
            modifier = Modifier.padding(20.dp),
            onRemoveClick = {},
            onEscapeTypeChange = {}
        )
    }
}


private fun main() {
    previewApp {
        Preview_EsField()
    }
}
