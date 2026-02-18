package com.x12q.mocker123._1_app._1_main_screen

import com.x12q.mocker123._1_app._1_main_screen.di.MainScreenSubComponent
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class MainScreenViewModelFactory @Inject constructor(
    private val subCompBuilderProvider: Provider<MainScreenSubComponent.Builder>
){
    fun create(): MainScreenViewModel {
        return subCompBuilderProvider.get().build().mainViewModel()
    }
}
