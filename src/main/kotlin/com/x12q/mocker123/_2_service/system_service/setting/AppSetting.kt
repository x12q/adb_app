package com.x12q.mocker123._2_service.system_service.setting

import com.x12q.common_utils.ForTestOnly
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

interface AppSetting {
    val isDarkThemeFlow: Flow<Boolean>
    val isDarkTheme: Boolean
    fun setIsDarkTheme(isDarkTheme: Boolean)

    val appPackageNameFlow: Flow<String?>
    val appPackageName: String?
    fun saveAppPackageName(appPackageName: String?)


    fun saveStringValue(key: String, value: String)
    fun loadStringValueFlow(key: String): Flow<String?>
    fun loadStringValue(key:String):String?

    companion object {
        @OptIn(ForTestOnly::class)
        fun forPreview(): AppSetting {
            return AppSettingForPreview()
        }
    }
}


@ForTestOnly
private class AppSettingForPreview : AppSetting {

    private val isDarkThemeMsFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isDarkThemeFlow: StateFlow<Boolean> = isDarkThemeMsFlow
    override val isDarkTheme: Boolean get() = isDarkThemeMsFlow.value

    override fun setIsDarkTheme(isDarkTheme: Boolean) {
        isDarkThemeMsFlow.value = isDarkTheme
    }

    private val appPackNameMsFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    override val appPackageNameFlow: StateFlow<String?> = appPackNameMsFlow
    override val appPackageName: String? get() = appPackNameMsFlow.value

    override fun saveAppPackageName(appPackageName: String?) {
        appPackNameMsFlow.value = appPackageName
    }

    private val strMapFlow: MutableStateFlow<Map<String,String>> = MutableStateFlow(emptyMap())

    override fun saveStringValue(key: String, value: String) {
        strMapFlow.update {
            it + (key to value)
        }
    }

    override fun loadStringValueFlow(key: String): Flow<String?> {
        return strMapFlow.map { it[key] }
    }

    override fun loadStringValue(key: String): String? {
        return strMapFlow.value[key]
    }
}
