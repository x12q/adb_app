package com.x12q.mocker123.app.main_screen.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.x12q.common_di.di.viewmodel_di.LocalViewModeFactoryProvider
import com.x12q.mocker123.app.di.LocalAppComponentProvider

val LocalMainScreenSubComponentProvider: ProvidableCompositionLocal<MainScreenSubComponent?> = compositionLocalOf { null }

@Composable
fun WithMainScreenComponent(content: @Composable (MainScreenSubComponent) -> Unit) {
    val appComponent = LocalAppComponentProvider.current
        ?: throw IllegalStateException("AppComponent is NOT available.WithMainScreenComponent must be called where AppComponent is available")

    val mainScreenSubComp = remember { appComponent.mainScreenSubComponentFactory().createMainScreenComponent() }
    val viewModelFactoryProvider = mainScreenSubComp.getViewModelFactoryProvider()

    CompositionLocalProvider(
        LocalMainScreenSubComponentProvider provides mainScreenSubComp,
        LocalViewModeFactoryProvider provides viewModelFactoryProvider,
        content = {
            content(mainScreenSubComp)
        }
    )
}