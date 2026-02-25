package com.x12q.mocker123.app.theme

import androidx.compose.ui.graphics.Color
import com.x12q.common_ui.theme.BaseColors

data class AdbNotificationColor(
    val appBackground: Color = Color(0xFF0F172A),
    val packageNameBackground: Color = Color(0xFF1E293B),
    val packageNameBorder: Color = Color(0xFF334155),
    val sectionBackground: Color = Color(0xFF0F172A),
    val sectionBorder: Color = Color(0xFF1E293B),
    val sectionIconTint: Color = Color(0xFF195DE6),
    val button: Color = Color(0xFF195DE6),
    val buttonText: Color = Color.White,
    val sectionTitle: Color = Color(0xFFF1F5F9),
    val sectionSubTitle: Color = Color(0xFF94A3B8),
    val labelText1: Color = Color(0xFF94A3B8),
    val iconTintOnButton: Color = Color.White,
    val nonEditContentText: Color = Color(0xFFCBD5E1),
    val nonEditTextBoxBackground: Color = Color(0xFF020617),
    val contentText: Color = Color.White,
    val tableHeaderText: Color = Color(0xFF94A3B8),
    val tableContentText: Color = Color(0xFFF1F5F9),
    val tableContentTextCursor: Color = Color.Cyan,
    val tableHeaderBackground: Color = Color(0xFF1E293B),
    val tableIconTint: Color = Color(0xFF94A3B8),
    val tableDivider: Color = Color(0xFF1E293B),
) {
    companion object {
        val dark = AdbNotificationColor()
        val default = dark
    }
}

data class AppColor(
    val textBoxBorderColor: Color,
    val adbNotificationColor: AdbNotificationColor,
) {
    companion object {
        val dark = AppColor(BaseColors.Dark.default.onSurface2, adbNotificationColor = AdbNotificationColor.dark)
        val default = dark
    }
}