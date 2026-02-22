package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._1_package_name_section.PackageNameSection
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._2_manifest_section.ManifestSection
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.AdbSection
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._4_adb_output.AdbOutputView
import com.x12q.common_ui.spacer.VSpacer
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfileId

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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BaseTheme.colors.baseColors.surface1)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        VSpacer(0.dp)
        PackageNameSection(viewModel.packageNameSectionViewModel)
        ManifestSection(viewModel.manifestSectionViewModel)

        Row (horizontalArrangement = Arrangement.spacedBy(15.dp)){
            AdbSection(viewModel.adbSectionViewModel,modifier = Modifier.weight(0.5f))
            AdbOutputView(viewModel.adbOutputViewModel, modifier = Modifier.weight(0.5f))
        }
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
