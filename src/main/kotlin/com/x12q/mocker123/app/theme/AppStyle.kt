package com.x12q.mocker123.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.x12q.common_ui.theme.BaseTheme
import com.x12q.common_ui.theme.BaseTypography.jetBrainMonoFontFamily


object AppStyle {
    val sectionTitle: TextStyle
        @Composable get() = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = jetBrainMonoFontFamily,
            lineHeight = 1.4.sp,
            letterSpacing = 0.1.sp,
        )

    val sectionSubTitle: TextStyle
        @Composable get() = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Thin,
            fontFamily = jetBrainMonoFontFamily,
            lineHeight = 1.4.sp,
            letterSpacing = 0.1.sp,
        )

    val sectionSubTitleSpanStyle: SpanStyle
        @Composable get() = SpanStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Thin,
            fontFamily = jetBrainMonoFontFamily,
            letterSpacing = 0.1.sp,
        )

    val tableHeader: TextStyle
        @Composable get() = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = jetBrainMonoFontFamily,
            letterSpacing = 0.5.sp,
        )

    val content: TextStyle
        @Composable get() = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = jetBrainMonoFontFamily,
            letterSpacing = 0.5.sp,
        )
}