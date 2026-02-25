package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_output

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbOutput
import com.x12q.common_ui.box.BasicBorderContentBox
import com.x12q.common_ui.preview_views.PreviewBoxOnSurface
import com.x12q.mocker123.app.main_screen.adb_profile_screen.SectionBox
import com.x12q.common_ui.theme.BaseTheme
import kotlinx.datetime.Clock

@Composable
fun AdbOutputView(
    vm: AdbOutputViewModel,
    modifier: Modifier = Modifier,
) {
    val logs by vm.logListFlow.collectAsState()
    AdbOutputView(
        logs = logs,
        onClearLogClick = vm::clearLog,
        modifier = modifier,
    )
}

@Composable
fun AdbOutputView(
    logs: List<AdbOutput>,
    onClearLogClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SectionBox(modifier) {
    BasicBorderContentBox(
        modifier = Modifier
            .background(BaseTheme.colors.baseColors.surface2)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            modifier = modifier
        ) {
            items(logs) { log ->
                AdbOutputText(log, modifier = Modifier.padding(10.dp))
            }
        }
        ClearLogButton(onClick = onClearLogClick, modifier = Modifier.align(Alignment.TopEnd))
    }
    }
}


@Composable
internal fun AdbOutputText(log: AdbOutput, modifier: Modifier = Modifier) {
    Text(
        text = log.output,
        style = BaseTheme.typography.content.copy(
            BaseTheme.colors.baseColors.textOnSurface2
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun Preview_AdbOutputView() {
    PreviewBoxOnSurface {
        AdbOutputView(
            logs = """
            import androidx.compose.desktop.ui.tooling.preview.Preview
            import androidx.compose.foundation.layout.Box
            import androidx.compose.runtime.Composable
            import androidx.compose.ui.Alignment
            import androidx.compose.ui.Modifier
            import com.x12q.common_ui.text.SelectableBoxedText
        """.trimIndent().split("\n").map { AdbOutput(it, Clock.System.now()) },
            onClearLogClick = {}
        )
    }
}
