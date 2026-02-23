package com.x12q.mocker123._1_app._1_main_screen.di

import com.russhwolf.settings.PreferencesSettings
import com.x12q.common_di.di.viewmodel_di.ViewModelFactory
import com.x12q.common_di.di.viewmodel_di.ViewModelFactoryContainer
import com.x12q.common_di.di.viewmodel_di.ViewModelFactoryProvider
import com.x12q.mocker123.di.AppScope
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class AppGlobalComponent {

    abstract val viewModelFactoryProvider: ViewModelFactoryProvider

    @Provides
    @SingleIn(AppScope::class)
    fun provideViewModelFactoryProvider(
        factories: Set<ViewModelFactory> = emptySet(),
    ): ViewModelFactoryProvider {
        return ViewModelFactoryProvider.from(
            name = "AppGlobalComponent.ViewModelFactoryProvider",
            containers = listOf(
                ViewModelFactoryContainer(
                    factories = factories,
                    assistedInjectFactories = emptySet(),
                )
            )
        )
    }

    @Provides
    @SingleIn(AppScope::class)
    fun provideSetting(): PreferencesSettings {
        return PreferencesSettings.Factory().create("mocker_123_setting")
    }
}
