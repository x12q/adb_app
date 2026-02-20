package com.x12q.mocker123._1_app._1_main_screen

import androidx.compose.foundation.ContextMenuDataProvider
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.no_name_tab_place_holder
import com.x12q.adb_app.generated.resources.no_profile
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreen
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreenViewModel
import com.x12q.mocker123._1_app._1_main_screen._1_tab_view.TabLayout
import com.x12q.mocker123._1_app._1_main_screen._1_tab_view.TabView
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotLoadProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepo
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.common_ui.utils.DataLoadingStatus
import com.x12q.common_ui.utils.LoadError
import com.x12q.common_ui.utils.LoadedData
import com.x12q.common_ui.utils.Loading
import com.x12q.common_ui.window.CommonWindow
import com.x12q.mocker123._1_app._1_main_screen._1_tab_view.TabBar
import org.jetbrains.compose.resources.stringResource


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    modifier: Modifier = Modifier
) {
    val repoCont = viewModel.adbProfileRepoContainer
    val initLoad: MutableState<DataLoadingStatus<Unit, CannotLoadProfile>> = remember { mutableStateOf(Loading) }

    LaunchedEffect(Unit) {
        // delay(1000) // delay for dramatic effect
        repoCont.loadProfiles().collect {
            initLoad.value = it
        }
    }

    when (initLoad.value) {
        is LoadError -> {
            ErrorScreen("Impossible: Unable to load data or create init data")
        }

        is LoadedData -> {

            val profileScreenViewModels by viewModel.profileViewModelsFlow.collectAsState()
            val selected by viewModel.selectedViewModel.collectAsState()

            MainScreen(
                selectedAdbProfileScreenVM = selected,
                adbProfile = profileScreenViewModels,
                onAddTabClick = viewModel::onAddClick,
                onCloseTabClick = viewModel::onCloseTabClick,
                onSelect = viewModel::onSelect,
                modifier = modifier,
            )
        }

        Loading -> {
            LoadingScreen()
        }
    }
}

@Composable
fun MainScreen(
    selectedAdbProfileScreenVM: AdbProfileScreenViewModel?,
    adbProfile: List<AdbProfileScreenViewModel>,
    onAddTabClick: () -> Unit,
    onSelect: (AdbProfileScreenViewModel) -> Unit,
    onCloseTabClick: (AdbProfileRepo) -> Unit,
    modifier: Modifier = Modifier
) {

    TabLayout(
        modifier = modifier,
        allTabItems = adbProfile,
        tabItemView = { currentItem ->
            TabView(
                label = currentItem.profileDisplayNameFlow.collectAsState().value
                    ?: stringResource(Res.string.no_name_tab_place_holder),
                isSelected = currentItem == selectedAdbProfileScreenVM,
                onClick = {
                    onSelect(currentItem)
                },
                onCloseClick = {
                    onCloseTabClick(currentItem.adbProfileRepo)
                },
                onLabelChange = { newLabel ->
                    currentItem.changeProfileName(newLabel.takeIf { it.isNotEmpty() && it.isNotBlank() })
                },
                onRenameSelect = {
                    onSelect(currentItem)
                }
            )
        },
        content = {
            if (selectedAdbProfileScreenVM != null) {
                AdbProfileScreen(selectedAdbProfileScreenVM)
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
