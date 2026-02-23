package com.x12q.mocker123.app.main_screen.di

import com.x12q.common_di.di.viewmodel_di.ViewModelAssistedFactory
import com.x12q.common_di.di.viewmodel_di.ViewModelFactory
import com.x12q.common_di.di.viewmodel_di.ViewModelFactoryContainer
import com.x12q.common_di.di.viewmodel_di.ViewModelFactoryProvider
import com.x12q.mocker123.app.di.AppComponent
import com.x12q.mocker123.app.title_bar.di.TitleBarSubComponent
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn


@ContributesSubcomponent(MainScreenSubComponent.Scope::class)
@SingleIn(MainScreenSubComponent.Scope::class)
interface MainScreenSubComponent {
    object Scope
    @ContributesSubcomponent.Factory(AppComponent.Scope::class)
    interface Factory {
        fun createMainScreenComponent(): MainScreenSubComponent
    }

    fun titleBarSubComponentFactory(): TitleBarSubComponent.Factory

    fun getViewModelFactoryProvider(): @FromMainScreenSubComponent ViewModelFactoryProvider

    @Provides
    @FromMainScreenSubComponent
    @SingleIn(MainScreenSubComponent.Scope::class)
    fun viewModelFactoryContainer(
        factorySet: Set<ViewModelFactory> = emptySet(),
        assistedFactory:Set<ViewModelAssistedFactory> = emptySet(),
    ): ViewModelFactoryProvider {
        return ViewModelFactoryProvider.from(
            name = "MainScreenSubComponent.ViewModelFactoryProvider",
            containers = listOf(ViewModelFactoryContainer(factories = factorySet, assistedInjectFactories = assistedFactory))
        )
    }
}