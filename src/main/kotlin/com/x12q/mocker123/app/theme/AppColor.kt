package com.x12q.mocker123.app.theme

import androidx.compose.ui.graphics.Color
import com.x12q.common_ui.theme.BaseColors

data class AdbNotificationColor(
    val background1: Color = Color(0xFF0F172A),
    val background2: Color = Color(0xFF0F172A),
    val button: Color = Color(0xFF195DE6),
    val buttonText: Color = Color.White,
    val labelText0: Color = Color(0xFFF1F5F9),
    val labelText1: Color = Color(0xFF94A3B8),
    val iconTint1: Color = Color(0xFF195DE6),
    val iconTintOnButton: Color = Color.White,
    val nonEditContentText: Color = Color(0xFFCBD5E1),
    val nonEditTextBoxBackground: Color = Color(0xFF020617),
    val contentText: Color = Color.White,
    val tableHeaderText: Color = Color(0xFF94A3B8),
    val tableHeaderBackground: Color = Color(0xFF94A3B8),
    val tableIconTint: Color = Color(0xFF94A3B8),
) {
    companion object {
        val dark = AdbNotificationColor()
        val default = dark
    }
}

data class AppColor(
    val textBoxBorderColor: Color,


    ) {
    companion object {
        val dark = AppColor(BaseColors.Dark.default.onSurface2)
        val light = AppColor(BaseColors.Light.default.onSurface2)
        val default = dark
    }
}