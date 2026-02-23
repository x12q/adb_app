package com.x12q.mocker123.app.main_screen.adb_profile_screen.package_name_section

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.app_package_name_placeholder
import com.x12q.adb_app.generated.resources.package_name_section_title
import com.x12q.common_ui.input_field.InputFieldWithLabel
import com.x12q.common_ui.preview_views.PreviewBigBoxOnSurface
import com.x12q.common_ui.preview_views.previewApp
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
    InputFieldWithLabel(
        label = stringResource(Res.string.package_name_section_title),
        text = packageName ?: "",
        hint = stringResource(Res.string.app_package_name_placeholder),
        onTextChange = { newText ->
            onPackageNameChange(newText)
        },
        modifier = modifier,
        fieldModifier = Modifier.fillMaxWidth()
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
