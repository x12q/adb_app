package com.x12q.mocker123.app.main_screen.tab_view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.x12q.adb_app.generated.resources.Res
import com.x12q.adb_app.generated.resources.rename_context_menu_item_label
import com.x12q.common_ui.button.ButtonColorIndication
import com.x12q.common_ui.corner6Border
import com.x12q.common_ui.preview_views.PreviewBoxOnSurface
import com.x12q.common_ui.preview_views.previewApp
import com.x12q.common_ui.text.ContentText
import com.x12q.common_ui.theme.BaseTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun TabView(
    label: String,
    onLabelChange: (String) -> Unit,
    onRenameSelect: () -> Unit,
    isSelected: Boolean,
    onClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val backgroundColor = if (isSelected) {
        BaseTheme.colors.baseColors.faintOnSurface2
    } else {
        BaseTheme.colors.baseColors.surface2
    }
    val rename = stringResource(Res.string.rename_context_menu_item_label)
    var showEditableLabel by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    ContextMenuArea(items = {
        listOf(
            ContextMenuItem(rename) {
                showEditableLabel = true
                onRenameSelect()
            },
        )
    }) {
        Box(
            modifier = modifier
                .corner6Border(color = backgroundColor)
                .background(backgroundColor)
                .width(150.dp)
                .height(40.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication =if(isSelected){
                        ButtonColorIndication.transparent()
                    }else{
                        ButtonColorIndication.forIconButton()
                    },
                    onClick = onClick
                )
            ,
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(end = 43.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                if (showEditableLabel) {

                    LaunchedEffect(Unit) {
                        /**
                         * Cannot put this focus request line into the Rename item.
                         * It will crash the app.
                         */
                        focusRequester.requestFocus()
                    }

                    /**
                     *  This is a work around the behavior of focus.
                     *  The first focus is always InActive (not focus).
                     *  Need to ignore that.
                     */
                    var ignoreFirstFocusState by remember { mutableStateOf(true) }

                    TabLabelTextField(
                        label = label,
                        onLabelChange = onLabelChange,
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                if (!focusState.isFocused && ignoreFirstFocusState) {
                                    ignoreFirstFocusState = false
                                } else {
                                    if (!focusState.isFocused) {
                                        showEditableLabel = false
                                    }
                                }
                            }
                            .onKeyEvent { keyEvent ->
                                /**
                                 * While the label is being edited, press Enter will end the editing.
                                 */
                                if (keyEvent.key == Key.Enter) {
                                    showEditableLabel = false
                                    true
                                } else {
                                    false
                                }
                            }
                    )
                } else {
                    ContentText(label, Modifier.padding(start = 15.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            CloseTabButton(isSelected, onCloseClick, modifier = Modifier.align(Alignment.CenterEnd))
        }
    }
}

@Preview
@Composable
private fun Preview_TabView() {
    PreviewBoxOnSurface {
        Row(Modifier.padding(100.dp)) {
            var t2 by remember { mutableStateOf(false) }
            var t1 by remember { mutableStateOf(false) }
            var t3 by remember { mutableStateOf(false) }
            var tab1 by remember { mutableStateOf("Tab 1") }
            TabView(
                label = "Tab 2",
                isSelected = t2,
                onClick = { t2 = !t2 },
                onCloseClick = {},
                onLabelChange = {},
                onRenameSelect = {},
            )
            TabView(
                label = tab1,
                isSelected = t1,
                onClick = { t1 = !t1 },
                onCloseClick = {},
                onLabelChange = { tab1 = it },
                onRenameSelect = {},
            )
            TabView(
                label = "Tab 3",
                isSelected = t3,
                onClick = { t3 = !t3 },
                onCloseClick = {},
                onLabelChange = {},
                onRenameSelect = {},
            )
        }
    }
}


fun main() {
    previewApp {
        Preview_TabView()
    }
}
