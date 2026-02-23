package com.x12q.mocker123.app.title_bar.di

import com.x12q.common_di.di.viewmodel_di.ViewModelAssistedFactory
import com.x12q.common_di.di.viewmodel_di.ViewModelFactory
import com.x12q.common_di.di.viewmodel_di.ViewModelFactoryContainer
import com.x12q.common_di.di.viewmodel_di.ViewModelFactoryProvider
import com.x12q.mocker123.app.main_screen.di.MainScreenSubComponent
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn


@ContributesSubcomponent(TitleBarSubComponent.Scope::class)
@SingleIn(TitleBarSubComponent.Scope::class)
interface TitleBarSubComponent {
    object Scope
    @ContributesSubcomponent.Factory(MainScreenSubComponent.Scope::class)
    interface Factory {
        fun createTopBarComponent(): TitleBarSubComponent
    }

    fun getViewModelFactoryProvider(): @FromTitleBarSubComponent ViewModelFactoryProvider

    @Provides
    @FromTitleBarSubComponent
    @SingleIn(TitleBarSubComponent.Scope::class)
    fun viewModelFactoryContainer(
        factorySet: Set<ViewModelFactory> = emptySet(),
        assistedFactory: Set<ViewModelAssistedFactory> = emptySet(),
    ): ViewModelFactoryProvider {
        return ViewModelFactoryProvider.from(
            name = "TopBarSubComponent.ViewModelFactoryProvider",
            containers = listOf(ViewModelFactoryContainer(factories = factorySet, assistedInjectFactories = assistedFactory))
        )
    }
}