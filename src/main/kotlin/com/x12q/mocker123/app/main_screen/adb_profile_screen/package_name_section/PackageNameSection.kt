package com.x12q.mocker123.app.main_screen.adb_profile_screen.package_name_section

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.app_package_name_placeholder
import com.x12q.adb_app.generated.resources.package_name_section_title
import com.x12q.adb_app.generated.resources.target_application
import com.x12q.common_ui.box.CenterAlignBox
import com.x12q.common_ui.corner6Border
import com.x12q.common_ui.input_field.BoxedTextField
import com.x12q.common_ui.input_field.InputFieldWithLabel
import com.x12q.common_ui.input_field.TextContainerWithHint
import com.x12q.common_ui.preview_views.PreviewBigBoxOnSurface
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.spacer.VSpacer
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.text.LabelText
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.mocker123.app.main_screen.adb_profile_screen.SectionTitle
import com.x12q.mocker123.app.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun PackageNameSection(
    packageNameSectionViewModel: PackageNameSectionViewModel,
) {
    PackageNameSection(
        packageName = packageNameSectionViewModel.packageNameFlow.collectAsState().value,
        onPackageNameChange = packageNameSectionViewModel::setPackageName,
    )
}

@Composable
fun PackageNameSection(
    packageName: String?,
    onPackageNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier) {
        SectionTitle(stringResource(Res.string.target_application))
        LabelText(stringResource(Res.string.package_name_section_title))
        VSpacer(10.dp)

        PackageNameTextField(packageName ?: "",
            hint = stringResource(Res.string.app_package_name_placeholder),
            readOnly = false,
            onTextChange = { newText ->
                onPackageNameChange(newText)
            },
            modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun PackageNameTextField(
    text: String?,
    modifier: Modifier = Modifier,
    hint: String? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    onTextChange: (newText: String) -> Unit,
) {
    val textStyle: TextStyle = BaseTheme.typography.content
    BasicTextField(
        value = text ?: "",
        onValueChange = onTextChange,
        singleLine = singleLine,
        readOnly = readOnly,
        textStyle = textStyle,
        cursorBrush = SolidColor(BaseTheme.colors.baseColors.strongOnSurface1),
        decorationBox = { textView ->
            val showHint = hint != null && text.isNullOrEmpty()
            CenterAlignBox(
                Modifier
                    .corner6Border(color = AppTheme.appColor.adbNotificationColor.packageNameBorder)
                    .background(AppTheme.appColor.adbNotificationColor.packageNameBackground)
                    .padding(vertical = 10.dp)

            ) {
                if (showHint) {
                    Text(hint, style = textStyle, color = BaseTheme.colors.baseColors.textHintOnSurface1)
                } else {
                    textView()
                }
            }
        },
        modifier = modifier
    )
}


@Preview
@Composable
private fun Preview_PackageNameSection() {
    PreviewBigBoxOnSurface {
        var txt: String? by remember { mutableStateOf(null) }
        PackageNameSection(
            packageName = txt,
            onPackageNameChange = {
                txt = it
            }
        )
    }
}

private fun main() {
    previewApp {
        Preview_PackageNameSection()
    }
}
