package com.x12q.mocker123.app.main_screen.adb_profile_screen.manifest_section

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.app_package_name_placeholder
import com.x12q.adb_app.generated.resources.copied
import com.x12q.adb_app.generated.resources.manifest_config
import com.x12q.adb_app.generated.resources.manifest_section_title
import com.x12q.common_ui.box.SurfaceBox
import com.x12q.common_ui.corner4Border
import com.x12q.common_ui.corner6Border
import com.x12q.common_ui.toast.Toast
import com.x12q.common_ui.toast.ToastDuration
import com.x12q.common_ui.toast.ToastText
import com.x12q.common_ui.text.LabelText
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.mocker123.app.main_screen.adb_profile_screen.NonEditSelectableTextBox
import com.x12q.mocker123.app.main_screen.adb_profile_screen.SectionIcon
import com.x12q.mocker123.app.main_screen.adb_profile_screen.SectionTitle
import com.x12q.mocker123.app.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.Text


@Composable
fun ManifestSection(
    viewModel: ManifestSectionViewModel
) {
    ManifestSection(
        packageName = viewModel.packageNameFlow.collectAsState().value,
        onCopyClick = { text ->
            viewModel.onClickCopy(text)
        }
    )
}

@Composable
fun ManifestSection(
    packageName: String?,
    onCopyClick: (text: String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {

        SectionTitle(stringResource(Res.string.manifest_config), icon = {
            SectionIcon(Res.drawable.manifest_config)
        })

        LabelText(stringResource(Res.string.manifest_section_title))

        Box {
            val actualPackageName = packageName ?: stringResource(Res.string.app_package_name_placeholder)

            ManifestTextBox(actualPackageName, Modifier.fillMaxWidth())

            val tobeCopiedText = buildAnnotatedManifestText(actualPackageName).text

            var showToast by remember { mutableStateOf(false) }

            CopyButton(
                onCopyClick = {
                    onCopyClick(tobeCopiedText)
                    showToast = true
                },
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
            )

            Toast(
                initVisibility = showToast,
                duration = ToastDuration.MEDIUM,
                onVisibilityChange = {
                    showToast = it
                },
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 15.dp)
            ) {
                ToastText(stringResource(Res.string.copied))
            }
        }
    }
}

@Composable
fun ManifestTextBox(
    packageName: String,
    modifier: Modifier = Modifier
) {
    NonEditSelectableTextBox(buildAnnotatedManifestText(packageName), modifier)
}


@Preview
@Composable
private fun Preview_StaticTextField() {
    SurfaceBox {
        var showCopyIcon by remember { mutableStateOf(true) }
        ManifestSection(
            packageName = "com.123.app.abc",
            onCopyClick = {
                showCopyIcon = !showCopyIcon
            }
        )
    }
}

fun main() {
    previewApp {
        Preview_StaticTextField()
    }
}
