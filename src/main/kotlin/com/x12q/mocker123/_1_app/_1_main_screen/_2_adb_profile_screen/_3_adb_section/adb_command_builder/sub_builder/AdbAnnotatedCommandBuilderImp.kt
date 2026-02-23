package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.AnnotatedStrResult
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.es_formatter.EsFormatter
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.EsData
import com.x12q.mocker123.di.AppScope
import java.nio.file.Path
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class AdbAnnotatedCommandBuilderImp(
    val esFormatter: EsFormatter,
) : AdbAnnotatedCommandBuilder {

    override fun build(
        adbProfile: AdbProfile,
        adbPath: Path,
        appPackageNamePlaceholder: String
    ): AnnotatedString {
        val annotatedCommand = makeBaseAnnotatedCommand(
            appPackageName = adbProfile.packageName,
            adbPath = adbPath,
            separator = " \\${System.lineSeparator()}    ",
            appPackageNamePlaceholder = appPackageNamePlaceholder,
            esList = adbProfile.esList,
        )
        return annotatedCommand
    }

    fun makeBaseAnnotatedCommand(
        appPackageName: String?,
        adbPath: Path,
        separator: String,
        appPackageNamePlaceholder: String,
        esList:List<EsData>,
    ): AnnotatedString {
        return buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Yellow)) {
                append("$adbPath")
            }
            append(" ")
            append("shell am broadcast")
            append(separator)
            append("-n ")
            withStyle(style = SpanStyle(color = Color.Yellow)) {
                append(appPackageName ?: appPackageNamePlaceholder)
            }
            append("/com.google.firebase.iid.FirebaseInstanceIdReceiver")
            append(separator)
            append("-c ")
            withStyle(style = SpanStyle(color = Color.Yellow)) {
                append(appPackageName ?: appPackageNamePlaceholder)
            }
            append(" -a com.google.android.c2dm.intent.RECEIVE")
            append(separator)
            withStyle(style = SpanStyle(color = Color.Yellow)){
                append(makeEsArgs(esList, separator))
            }
        }
    }

    fun makeEsArgs(esList: List<EsData>, separator: String):String {
        return esList.joinToString(separator) { es ->
            esFormatter.makeEsStringForPresentation(es)
        }
    }
}
