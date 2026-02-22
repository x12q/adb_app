package com.x12q.mocker123._1_app._1_main_screen.di

import com.russhwolf.settings.PreferencesSettings
import com.x12q.common_di.di.global.GlobalComponent
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(GlobalComponent.Scope::class)
@SingleIn(GlobalComponent.Scope::class)
abstract class AppGlobalComponent : GlobalComponent {

    @Provides
    @SingleIn(GlobalComponent.Scope::class)
    fun provideSetting(): PreferencesSettings {
        return PreferencesSettings.Factory().create("mocker_123_setting")
    }
}
