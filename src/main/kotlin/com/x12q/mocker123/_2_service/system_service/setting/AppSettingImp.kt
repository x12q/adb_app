package com.x12q.mocker123._2_service.system_service.setting

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.getBooleanFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalSettingsApi::class)
class AppSettingImp @Inject constructor(
    val settings: PreferencesSettings
): AppSetting {
    override val isDarkThemeFlow: Flow<Boolean> = settings.getBooleanFlow(IS_DARK_KEY,true)
    override val isDarkTheme: Boolean
        get() = settings.getBoolean(IS_DARK_KEY,true)

    override fun setIsDarkTheme(isDarkTheme: Boolean) {
        settings[IS_DARK_KEY] = isDarkTheme
    }

    override val appPackageNameFlow: Flow<String?> = settings.getStringOrNullFlow(APP_PACKAGE_KEY)

    override val appPackageName: String?
        get() = settings.getStringOrNull(APP_PACKAGE_KEY)

    override fun saveAppPackageName(appPackageName: String?) {
        settings[APP_PACKAGE_KEY] = appPackageName
    }

    override fun saveStringValue(key: String, value: String) {
        settings[key] = value
    }

    override fun deleteStringValue(key: String) {
        settings.remove(key)
    }

    override fun loadStringValueFlow(key: String): Flow<String?> {
        return settings.getStringOrNullFlow(key)
    }

    override fun loadStringValue(key: String): String? {
        return settings.getStringOrNull(key)
    }

    companion object{
        private const val IS_DARK_KEY = "IS_DARK_KEY"
        private const val APP_PACKAGE_KEY = "APP_PACKAGE_KEY"
    }

}
