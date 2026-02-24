package com.x12q.mocker123.app.main_screen.adb_profile_screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.x12q.common_ui.box.CenterAlignBox
import com.x12q.mocker123.app.main_screen.adb_profile_screen.package_name_section.PackageNameSection
import com.x12q.mocker123.app.main_screen.adb_profile_screen.manifest_section.ManifestSection
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.AdbSection
import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_output.AdbOutputView
import com.x12q.common_ui.spacer.VSpacer
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.spacer.HSpacer

/**
 * Idea setup:
 * - UI only takes in init information, then create viewmodel internally
 * - in order to create view model internally -> need a function like getVM to access the implicit viewmodel store
 * - need to provide view model store somehow
 * - view model store needs to be connected to the DI graph somehow
 * - the DI component must be provided by someone, somehow.
 */
@Composable
fun AdbProfileScreen(
    viewModel: AdbProfileScreenViewModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(BaseTheme.colors.baseColors.surface1)
            .padding(10.dp),
    ) {
        Column(Modifier.weight(0.3f)) {
            SectionBox(Modifier.fillMaxWidth()) {
                PackageNameSection(viewModel.packageNameSectionViewModel)
            }

            VSpacer(8.dp)

            SectionBox(Modifier.fillMaxSize()) {
                ManifestSection(viewModel.manifestSectionViewModel)
            }
        }

        HSpacer(8.dp)

        Column(Modifier.weight(0.7f)) {
            SectionBox(Modifier.weight(0.25f)) {
                AdbSection(viewModel.adbSectionViewModel, modifier = Modifier.fillMaxSize())
            }
            VSpacer(8.dp)

            SectionBox(Modifier.weight(0.25f)) {
                // command execution
            }
            VSpacer(8.dp)

            SectionBox(Modifier.weight(0.5f)) {
                AdbOutputView(viewModel.adbOutputViewModel, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun SectionBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .border(1.dp, Color.Red, BaseTheme.shapes.corner12)
            .clip(BaseTheme.shapes.corner12)
            .padding(10.dp),
        contentAlignment=contentAlignment,
    ) {
        content()
    }
}


@Preview
@Composable
private fun Preview_MainScreen() {
    AdbProfileScreen(
        AdbProfileScreenViewModel.forPreview()
    )
}

private fun main() {
    previewApp {
        Preview_MainScreen()
    }
}
