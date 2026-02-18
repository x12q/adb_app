package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreenViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.di.AdbModule
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepo
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(modules = [
    AdbModule::class,
])
@AdbProfileScreenScope
interface AdbProfileScreenScreenSubComponent {

    fun adbProfileScreenViewModel(): AdbProfileScreenViewModel

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun setAdbProfileRepo(adbProfileRepo: AdbProfileRepo):Builder
        fun build(): AdbProfileScreenScreenSubComponent
    }
}



