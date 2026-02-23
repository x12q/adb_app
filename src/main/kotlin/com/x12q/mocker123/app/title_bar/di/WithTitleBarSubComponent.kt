package com.x12q.mocker123.app.title_bar.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.x12q.common_di.di.viewmodel_di.LocalViewModeFactoryProvider
import com.x12q.mocker123.app.main_screen.di.LocalMainScreenSubComponentProvider

val LocalTopBarSubComponentProvider: ProvidableCompositionLocal<TitleBarSubComponent?> = compositionLocalOf { null }

@Composable
fun WithTitleBarSubComponent(content: @Composable (TitleBarSubComponent) -> Unit) {
    val mainScreenSubComp = LocalMainScreenSubComponentProvider.current
        ?: throw IllegalStateException("MainScreenSubComponent is NOT available. WithTitleBarSubComponent must be called where MainScreenSubComponent is available")

    val topBarSubComp = remember { mainScreenSubComp.titleBarSubComponentFactory().createTopBarComponent() }
    val viewModelFactoryProvider = topBarSubComp.getViewModelFactoryProvider()

    CompositionLocalProvider(
        LocalTopBarSubComponentProvider provides topBarSubComp,
        LocalViewModeFactoryProvider provides viewModelFactoryProvider,
        content = {
            content(topBarSubComp)
        }
    )
}
