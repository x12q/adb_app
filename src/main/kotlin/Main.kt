import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.x12q.mocker123.DaggerAppComponent
import com.x12q.mocker123._1_app._1_main_screen.MainScreen
import com.x12q.mocker123._1_app._1_main_screen._1_tab_view.TabBar
import com.x12q.mocker123._1_app._1_main_screen._1_tab_view.TabView
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls


fun main() {

    val comp = DaggerAppComponent.create()
    val repoCont = comp.adbProfileRepoContainer()
    val mainViewModel = comp.mainScreenViewModelFactory().create()

    application {
        BaseTheme(isDarkTheme = true) {
            CommonWindow(
                state = rememberWindowState(
                    size = DpSize(1200.dp, 800.dp),
                    placement = WindowPlacement.Floating,
                    position = WindowPosition(Alignment.Center)
                ),
                onCloseRequest = {
                    repoCont.save()
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


@ExperimentalLayoutApi
@Composable
internal fun DecoratedWindowScope.TitleBarView() {
    TitleBar(Modifier.newFullscreenControls(), ) {
        Row(Modifier.align(Alignment.Start)) {

        }

        Text(title)

        Row(Modifier.align(Alignment.End)) {
        }
    }
}