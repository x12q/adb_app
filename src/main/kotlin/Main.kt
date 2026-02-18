import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.x12q.mocker123.DaggerAppComponent
import com.x12q.mocker123._1_app._1_main_screen.MainScreen
import com.x12q.common_ui.theme.BaseTheme


fun main() {

    val comp = DaggerAppComponent.create()
    val repoCont = comp.adbProfileRepoContainer()
    val mainViewModel = comp.mainScreenViewModelFactory().create()

    application {
        Window(
            onCloseRequest = {
                repoCont.save()
                exitApplication()
            },
            state = rememberWindowState(
                size = DpSize(1200.dp,800.dp),
                placement = WindowPlacement.Floating,
                position = WindowPosition(Alignment.Center)
            ),
        ) {
            BaseTheme(isDarkTheme = true){
                MainScreen(mainViewModel)
            }
        }
    }
}
