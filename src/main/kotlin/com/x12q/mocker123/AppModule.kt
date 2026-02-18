package com.x12q.mocker123

import com.russhwolf.settings.PreferencesSettings
import com.x12q.mocker123._2_service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123._2_service.local_service.adb_profile.AdbProfileRepoContainerImp
import com.x12q.mocker123._1_app._1_main_screen.di.MainScreenSubComponent
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    subcomponents = [
        MainScreenSubComponent::class
    ]
)
interface AppModule{

    @Binds
    @Singleton
    fun AdbProfileContainer(i: AdbProfileRepoContainerImp):AdbProfileRepoContainer

    companion object{
        @Provides
        @Singleton
        fun setting(): PreferencesSettings {
            val factory: PreferencesSettings.Factory = PreferencesSettings.Factory()
            val setting = factory.create("mocker_123_setting")
            return setting
        }
    }
}
