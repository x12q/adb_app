package com.x12q.mocker123.app.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ApplicationScope

val LocalAppComponentProvider: ProvidableCompositionLocal<AppComponent?> = compositionLocalOf { null }

@Composable
fun ApplicationScope.WithAppComponent(
    content: @Composable AppScope.()->Unit,
){
    val appComponent = remember {
        AppComponent::class.create()
    }
    val appScope = remember {
        object : AppScope{
            override val appComponent: AppComponent = appComponent
        }
    }

    CompositionLocalProvider(
        LocalAppComponentProvider provides appComponent,
        content = {
            appScope.content()
        }
    )
}
