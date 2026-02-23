import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.no_name_tab_place_holder
import com.x12q.common_di.di.viewmodel_di.LocalViewModeFactoryProvider
import com.x12q.common_di.di.viewmodel_di.getVM
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.common_ui.window.CommonWindow
import com.x12q.mocker123.app.main_screen.MainScreen
import com.x12q.mocker123.app.main_screen.MainScreenViewModel
import com.x12q.mocker123.app.main_screen.tab_view.TabBar
import com.x12q.mocker123.app.main_screen.tab_view.TabView
import com.x12q.mocker123.di.AppGlobalComponent
import com.x12q.mocker123.di.create
import org.jetbrains.compose.resources.stringResource


fun main() {
    application {
        val component = remember { AppGlobalComponent::class.create() }

        CompositionLocalProvider(
            LocalViewModeFactoryProvider provides component.viewModelFactoryProvider
        ) {
            BaseTheme(isDarkTheme = true) {
                val mainViewModel = getVM<MainScreenViewModel>()
                CommonWindow(
                    state = rememberWindowState(
                        size = DpSize(1200.dp, 800.dp),
                        placement = WindowPlacement.Floating,
                        position = WindowPosition(Alignment.Center)
                    ),
                    onCloseRequest = {
                        mainViewModel.adbProfileRepoContainer.save()
                        exitApplication()
                    },
                    titleBarContent = {
                        val profileIds by mainViewModel.profileIdsFlow.collectAsState()
                        val selected by mainViewModel.selectedViewModel.collectAsState()
                        val selectedProfileId = selected?.adbProfileId
                        TabBar(
                            allTabItems = profileIds,
                            tabItemView = { profileId ->
                                val vm = mainViewModel.getViewModel(profileId)
                                TabView(
                                    label = vm?.profileDisplayNameFlow?.collectAsState()?.value
                                        ?: stringResource(Res.string.no_name_tab_place_holder),
                                    isSelected = profileId == selectedProfileId,
                                    onClick = { mainViewModel.onSelect(profileId) },
                                    onCloseClick = { mainViewModel.onCloseTabClick(profileId) },
                                    onLabelChange = { newLabel ->
                                        vm?.changeProfileName(newLabel.takeIf { it.isNotEmpty() && it.isNotBlank() })
                                    },
                                    onRenameSelect = { mainViewModel.onSelect(profileId) }
                                )
                            },
                            onAddClick = mainViewModel::onAddClick,
                            tailContent = null,
                            modifier = Modifier.padding(vertical = 3.dp)
                        )
                    }
                ) {
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}
