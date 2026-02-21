import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jetbrains.JBR
import com.sun.rowset.internal.Row
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.no_name_tab_place_holder
import com.x12q.mocker123.DaggerAppComponent
import com.x12q.mocker123._1_app._1_main_screen.MainScreen
import com.x12q.mocker123._1_app._1_main_screen._1_tab_view.TabBar
import com.x12q.mocker123._1_app._1_main_screen._1_tab_view.TabView
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.common_ui.window.CommonWindow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.foundation.modifier.border
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.Tooltip
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
                        modifier = Modifier.align(Alignment.Start)
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