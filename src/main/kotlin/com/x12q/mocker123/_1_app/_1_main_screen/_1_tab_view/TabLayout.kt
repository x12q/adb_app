package com.x12q.mocker123._1_app._1_main_screen._1_tab_view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.x12q.common_ui.CenterAlignRow
import com.x12q.common_ui.preview_views.PreviewBoxOnSurface
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.spacer.HSpacer
import com.x12q.common_ui.theme.BaseTheme
import kotlinx.coroutines.launch

/**
 * A layout that can host a tab bar (can hold multiple [tabItemView]), and a screen [content] below the tab bar
 */
@Composable
fun <T> TabLayout(
    allTabItems: List<T>,
    tabItemView: @Composable (item: T) -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    tailContent: (@Composable RowScope.() -> Unit)?,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .background(BaseTheme.colors.baseColors.surface2)
    ) {
        TabBar(allTabItems, tabItemView, onAddClick, tailContent, Modifier.padding(4.dp))
        content()
    }
}

@Composable
fun <T> TabBar(
    allTabItems: List<T>,
    tabItemView: @Composable (item: T) -> Unit,
    onAddClick: () -> Unit,
    tailContent: (@Composable RowScope.() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    CenterAlignRow(modifier
    ) {
        TabRow(
            allTabItems, tabItemView,
            modifier = Modifier.weight(1f)
        )
        HSpacer(3.dp)
        AddTabButton(onAddClick)
        tailContent?.invoke(this)
    }
}

@Composable
private fun <T> TabRow(
    allTabItems: List<T>,
    tabItemView: @Composable (item: T) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val cr = rememberCoroutineScope()
    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .pointerInput(Unit) {
                /**
                 * this allows scrolling the row with mouse scroll
                 */
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val scrollDelta = event.changes.firstOrNull()?.scrollDelta
                        if (scrollDelta != null && scrollDelta.y != 0f) {
                            cr.launch {
                                scrollState.scrollBy(scrollDelta.y * 20)
                            }
                        }
                    }
                }
            }
        ,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        allTabItems.map { e ->
            tabItemView(e)
        }
    }
}


@Preview
@Composable
private fun Preview_TabLayout() {
    PreviewBoxOnSurface {
        var st by remember { mutableStateOf("abc") }

        TabLayout(
            allTabItems = (1..30).map {
                "Tab $it"
            },
            tabItemView = { i ->
                TabView(
                    label = i,
                    isSelected = i == st,
                    onClick = {
                        st = i
                    },
                    onCloseClick = {},
                    onLabelChange = {},
                    onRenameSelect = {}
                )
            },
            content = {
                Box(Modifier.fillMaxWidth().height(100.dp).background(Color.Gray))
            },
            onAddClick = {},
            modifier = Modifier.padding(100.dp),
            tailContent = null
        )
    }
}

fun main() {
    previewApp {
        Preview_TabLayout()
    }
}
