package com.x12q.mocker123._2_service.system_service

import com.x12q.mocker123._2_service.system_service.adb_path_manager.AdbPathProvider
import com.x12q.mocker123._2_service.system_service.adb_path_manager.AdbPathProviderImp
import com.x12q.mocker123._2_service.system_service.setting.AppSetting
import com.x12q.mocker123._2_service.system_service.setting.AppSettingImp
import com.x12q.mocker123._2_service.system_service.command_runner.CommandRunner
import com.x12q.mocker123._2_service.system_service.command_runner.CommandRunnerImp
import com.x12q.mocker123._2_service.system_service.system_clipboard.SystemClipboardProvider
import com.x12q.mocker123._2_service.system_service.system_clipboard.SystemClipboardProviderImp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface SystemModule {
    @Binds
    @Singleton
    fun SystemClipboardProvider(i: SystemClipboardProviderImp): SystemClipboardProvider

    @Binds
    @Singleton
    fun setting(i: AppSettingImp): AppSetting

    @Binds
    @Singleton
    fun commandRunner(i: CommandRunnerImp): CommandRunner

    @Binds
    @Singleton
    fun AdbPathProvider(i: AdbPathProviderImp): AdbPathProvider
}
