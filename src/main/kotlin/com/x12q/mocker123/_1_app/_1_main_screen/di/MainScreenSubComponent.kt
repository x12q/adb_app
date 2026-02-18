package com.x12q.mocker123._1_app._1_main_screen.di

import com.x12q.mocker123._1_app._1_main_screen.MainScreenViewModel
import dagger.Subcomponent

@Subcomponent(modules = [
    MainScreenModule::class
])
@MainScreenScope
interface MainScreenSubComponent {

    fun mainViewModel(): MainScreenViewModel

    @Subcomponent.Builder
    interface Builder {
        fun build(): MainScreenSubComponent
    }
}



