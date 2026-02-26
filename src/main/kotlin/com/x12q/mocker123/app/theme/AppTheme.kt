package com.x12q.mocker123.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.x12q.common_ui.theme.BaseColor
import com.x12q.common_ui.theme.BaseShapes
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.common_ui.theme.BaseTypography
import com.x12q.common_ui.theme.LocalBaseColorProvider
import com.x12q.common_ui.theme.LocalBaseShapesProvider
import com.x12q.common_ui.theme.LocalBaseTypographyProvider
import com.x12q.common_ui.window.CommonTitleBarStyle
import org.jetbrains.jewel.intui.core.theme.IntUiDarkTheme
import org.jetbrains.jewel.intui.window.styling.dark
import org.jetbrains.jewel.intui.window.styling.light
import org.jetbrains.jewel.window.styling.LocalTitleBarStyle
import org.jetbrains.jewel.window.styling.TitleBarColors
import org.jetbrains.jewel.window.styling.TitleBarStyle

val LocalAppColorProvider = staticCompositionLocalOf { AppColor.default }
val LocalIsDarkProvider = staticCompositionLocalOf{ true }

object AppTheme {
    val baseTheme = BaseTheme
    val appColor: AppColor @Composable get() = LocalAppColorProvider.current
    val appStyle = AppStyle
}


@Composable
fun AppTitleBarStyle(isDark: Boolean): TitleBarStyle {
    return if (isDark) {
        TitleBarStyle.dark(
            colors = TitleBarColors.dark(
                backgroundColor = AppTheme.appColor.adbNotificationColor.appBackground,
                borderColor = AppTheme.appColor.adbNotificationColor.sectionBorder,
            )
        )
    } else {
        TitleBarStyle.light()
    }
}


@Composable
fun AppTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    BaseTheme(isDarkTheme = isDarkTheme) {
        val appColor = if (isDarkTheme) {
            AppColor.dark
        } else {
            AppColor.dark
        }
        CompositionLocalProvider(
            LocalAppColorProvider provides appColor,
            LocalIsDarkProvider provides isDarkTheme,
            content = content
        )
    }
}