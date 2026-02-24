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

val LocalAppColorProvider = staticCompositionLocalOf{ AppColor.default }

object AppTheme{
    val baseTheme = BaseTheme
    val appColor: AppColor @Composable get() = LocalAppColorProvider.current
    val appStyle = AppStyle
}




@Composable
fun AppTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    BaseTheme(isDarkTheme = isDarkTheme){
        val appColor = if(isDarkTheme){
            AppColor.dark
        }else{
            AppColor.dark
        }
        CompositionLocalProvider(
            LocalAppColorProvider provides appColor,
            content = content
        )
    }
}