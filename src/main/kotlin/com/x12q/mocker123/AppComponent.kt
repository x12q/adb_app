package com.x12q.mocker123

import com.x12q.mocker123._1_app._1_main_screen.MainScreenViewModelFactory
import com.x12q.mocker123._2_service.local_service.LocalServiceModule
import com.x12q.mocker123._2_service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123._2_service.system_service.SystemModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        SystemModule::class,
        LocalServiceModule::class,
    ],
)
@Singleton
interface AppComponent {
    fun mainScreenViewModelFactory(): MainScreenViewModelFactory
    fun adbProfileRepoContainer(): AdbProfileRepoContainer
}
