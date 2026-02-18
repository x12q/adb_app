package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di.AdbProfileScreenScreenSubComponent
import com.x12q.mocker123._1_app._1_main_screen.di.MainScreenScope
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepo
import javax.inject.Inject
import javax.inject.Provider

@MainScreenScope
class AdbProfileScreenViewModelFactory @Inject constructor(
    val subCompProvider: Provider<AdbProfileScreenScreenSubComponent.Builder>,
){
    fun create(adbProfileRepo: AdbProfileRepo):AdbProfileScreenViewModel{
        return subCompProvider.get()
            .setAdbProfileRepo(adbProfileRepo)
            .build()
            .adbProfileScreenViewModel()
    }
}
