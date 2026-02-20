package com.x12q.mocker123._1_app._1_main_screen

import androidx.compose.foundation.ContextMenuDataProvider
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.no_name_tab_place_holder
import com.x12q.adb_app.generated.resources.no_profile
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreen
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreenViewModel
import com.x12q.mocker123._1_app._1_main_screen._1_tab_view.TabLayout
import com.x12q.mocker123._1_app._1_main_screen._1_tab_view.TabView
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfileId
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotLoadProfile
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.theme.BaseTheme
import org.jetbrains.compose.resources.stringResource


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    modifier: Modifier = Modifier
) {
    val repoCont = viewModel.adbProfileRepoContainer
    val initLoad: MutableState<com.github.michaelbull.result.Result<Unit, CannotLoadProfile>?> = remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        initLoad.value = repoCont.loadProfiles2()
    }

    when (val result = initLoad.value) {
        null -> {
            LoadingScreen()
        }

        is Err -> {
            ErrorScreen("Impossible: Unable to load data or create init data")
        }

        is Ok -> {
            val profileIds by viewModel.profileIdsFlow.collectAsState()
            val selected by viewModel.selectedViewModel.collectAsState()

            MainScreen(
                selectedProfileId = selected?.adbProfileId,
                profileIds = profileIds,
                getViewModel = viewModel::getViewModel,
                onAddTabClick = viewModel::onAddClick,
                onCloseTabClick = viewModel::onCloseTabClick,
                onSelect = viewModel::onSelect,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun MainScreen(
    selectedProfileId: AdbProfileId?,
    profileIds: List<AdbProfileId>,
    getViewModel: (AdbProfileId) -> AdbProfileScreenViewModel?,
    onAddTabClick: () -> Unit,
    onSelect: (AdbProfileId) -> Unit,
    onCloseTabClick: (AdbProfileId) -> Unit,
    modifier: Modifier = Modifier
) {

    TabLayout(
        modifier = modifier,
        allTabItems = profileIds,
        tabItemView = { profileId ->
            val vm = getViewModel(profileId)
            TabView(
                label = vm?.profileDisplayNameFlow?.collectAsState()?.value
                    ?: stringResource(Res.string.no_name_tab_place_holder),
                isSelected = profileId == selectedProfileId,
                onClick = {
                    onSelect(profileId)
                },
                onCloseClick = {
                    onCloseTabClick(profileId)
                },
                onLabelChange = { newLabel ->
                    vm?.changeProfileName(newLabel.takeIf { it.isNotEmpty() && it.isNotBlank() })
                },
                onRenameSelect = {
                    onSelect(profileId)
                }
            )
        },
        content = {
            val selectedVm = selectedProfileId?.let { getViewModel(it) }
            if (selectedVm != null) {
                AdbProfileScreen(selectedVm)
            }else{
                EmptyDefaultScreen()
            }
        },
        onAddClick = onAddTabClick,
        tailContent = null
    )

}

@Composable
fun LoadingScreen() {
    Box(Modifier.fillMaxSize().background(BaseTheme.colors.baseColors.surface1), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = BaseTheme.colors.baseColors.primary, strokeWidth = 5.dp)
    }
}

@Composable
fun EmptyDefaultScreen() {
    Box(Modifier.fillMaxSize().background(BaseTheme.colors.baseColors.surface1), contentAlignment = Alignment.Center) {
        ContentText(stringResource(Res.string.no_profile))
    }
}

@Composable
fun ErrorScreen(errMsg: String) {
    Box(Modifier.fillMaxSize().background(BaseTheme.colors.baseColors.surface1), contentAlignment = Alignment.Center) {
        // TODO add UI to handle loading data error
        ContentText(errMsg)
    }
}

fun main() {
    previewApp {
        Row {
            ContextMenuDataProvider(items = {
                listOf(
                    // ContextMenuItem("Option 1"){},
                    // ContextMenuItem("Option 2"){}
                )
            }) {
                SelectionContainer {
                    Text("T1")
                }

                SelectionContainer {
                    Text("T2")
                }
            }
        }
    }
}
