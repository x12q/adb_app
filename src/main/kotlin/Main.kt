import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.common_ui.window.CommonWindow
import com.x12q.mocker123.app.main_screen.MainScreen
import com.x12q.mocker123.app.main_screen.di.WithMainScreenComponent
import com.x12q.mocker123.app.title_bar.tab_view.TabBar
import com.x12q.mocker123.app.title_bar.tab_view.TabView
import com.x12q.mocker123.app.di.WithAppComponent
import com.x12q.common_di.di.viewmodel_di.getVM
import com.x12q.mocker123.app.theme.AppTheme
import com.x12q.mocker123.app.title_bar.TitleBarViewModel
import com.x12q.mocker123.app.title_bar.di.WithTitleBarSubComponent
import org.jetbrains.compose.resources.stringResource


fun main() {
    application {
        AppTheme(isDarkTheme = true) {
            WithAppComponent {
                WithMainScreenComponent {
                    CommonWindow(
                        state = rememberWindowState(
                            size = DpSize(1200.dp, 800.dp),
                            placement = WindowPlacement.Floating,
                            position = WindowPosition(Alignment.Center)
                        ),
                        onCloseRequest = {
                            appComponent.getAdbProfileRepoContainer().save()
                            exitApplication()
                        },
                        titleBarContent = {
                            WithTitleBarSubComponent {
                                val titleBarViewModel: TitleBarViewModel = getVM()
                                val profileIds by titleBarViewModel.profileIdsFlow.collectAsState()
                                val selected by titleBarViewModel.selectedTabInfo.collectAsState()
                                val selectedProfileId = selected?.adbProfileId
                                TabBar(
                                    allTabItems = profileIds,
                                    tabItemView = { profileId ->
                                        val tabInfo = titleBarViewModel.getTabInfo(profileId)
                                        TabView(
                                            label = tabInfo?.profileDisplayNameFlow?.collectAsState()?.value
                                                ?: stringResource(Res.string.no_name_tab_place_holder),
                                            isSelected = profileId == selectedProfileId,
                                            onClick = { titleBarViewModel.onSelect(profileId) },
                                            onCloseClick = { titleBarViewModel.onCloseTabClick(profileId) },
                                            onLabelChange = { newLabel ->
                                                tabInfo?.changeProfileName(newLabel.takeIf { it.isNotEmpty() && it.isNotBlank() })
                                            },
                                            onRenameSelect = { titleBarViewModel.onSelect(profileId) }
                                        )
                                    },
                                    onAddClick = titleBarViewModel::onAddClick,
                                    tailContent = null,
                                    modifier = Modifier.padding(vertical = 3.dp)
                                )
                            }
                        }
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }
}
