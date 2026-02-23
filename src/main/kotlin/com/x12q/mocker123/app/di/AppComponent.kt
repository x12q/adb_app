package com.x12q.mocker123.app.di

import com.russhwolf.settings.PreferencesSettings
import com.x12q.common_di.di.viewmodel_di.ViewModelFactory
import com.x12q.common_di.di.viewmodel_di.ViewModelFactoryContainer
import com.x12q.common_di.di.viewmodel_di.ViewModelFactoryProvider
import com.x12q.mocker123.app.main_screen.di.MainScreenSubComponent
import com.x12q.mocker123.service.local_service.adb_profile.AdbProfileRepoContainer
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppComponent.Scope::class)
@SingleIn(AppComponent.Scope::class)
abstract class AppComponent {

    object Scope

    abstract val viewModelFactoryProvider: ViewModelFactoryProvider

    abstract fun getAdbProfileRepoContainer(): AdbProfileRepoContainer

    abstract fun mainScreenSubComponentFactory(): MainScreenSubComponent.Factory


    @Provides
    @SingleIn(Scope::class)
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
    @SingleIn(Scope::class)
    fun provideSetting(): PreferencesSettings {
        return PreferencesSettings.Factory().create("mocker_123_setting")
    }
}