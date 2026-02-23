package com.x12q.mocker123.app.main_screen.adb_profile_screen.manifest_section

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.x12q.common_ui.theme.BaseTheme

private val _1st_PartOfManifest = """
<receiver
      android:name="com.google.firebase.iid.FirebaseInstanceIdReceiver"
      android:exported="true"
      android:permission="@null"
      tools:ignore="ExportedReceiver"
      tools:replace="android:permission">
  <intent-filter>
      <action android:name="com.google.android.c2dm.intent.RECEIVE" />
      <category android:name="
""".trimIndent()

private val _2nd_PartOfManifest = """
" />
  </intent-filter>
</receiver>
""".trimIndent()

@Composable
fun buildAnnotatedManifestText(
    packageName: String
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style =BaseTheme.typography.contentSpanStyle){
            append(_1st_PartOfManifest)
        }
        withStyle(style = BaseTheme.typography.highlightedContentSpanStyle.copy(
            color = Color.Yellow
        )) { append(packageName) }

        withStyle(style =BaseTheme.typography.contentSpanStyle){
            append(_2nd_PartOfManifest)
        }
    }
}
