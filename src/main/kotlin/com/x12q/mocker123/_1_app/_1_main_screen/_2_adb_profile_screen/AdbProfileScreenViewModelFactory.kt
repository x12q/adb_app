package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di.AdbProfileScreenSubComponent
import com.x12q.mocker123._1_app._1_main_screen.di.MainScreenScope
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfileId
import javax.inject.Inject
import javax.inject.Provider

@MainScreenScope
class AdbProfileScreenViewModelFactory @Inject constructor(
    val subCompProvider: Provider<AdbProfileScreenSubComponent.Builder>,
){
    fun create(adbProfileId: AdbProfileId): AdbProfileScreenViewModel {
        return subCompProvider.get()
            .setAdbProfileId(adbProfileId)
            .build()
            .adbProfileScreenViewModel()
    }
}
