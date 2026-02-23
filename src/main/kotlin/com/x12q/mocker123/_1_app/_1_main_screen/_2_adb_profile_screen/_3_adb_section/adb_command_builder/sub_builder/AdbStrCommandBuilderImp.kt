package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.AdbCommandBuilderError
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.es_formatter.EsFormatter
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.EsData
import com.x12q.mocker123.di.AppGlobalComponent
import java.nio.file.Path
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@ContributesBinding(AppGlobalComponent.Scope::class)
@SingleIn(AppGlobalComponent.Scope::class)
class AdbStrCommandBuilderImp(
    val esFormatter: EsFormatter,
): AdbStrCommandBuilder {
    override fun build(
        adbProfile: AdbProfile,
        adbPath: Path,
        appPackageNamePlaceholder: String
    ): Result<String, AdbCommandBuilderError> {
        val appPackageName: String? = adbProfile.packageName
        val command = appPackageName?.let {
            val command = listOf(
                "$adbPath shell am broadcast",
                "-n $appPackageName/com.google.firebase.iid.FirebaseInstanceIdReceiver",
                "-c $appPackageName  -a com.google.android.c2dm.intent.RECEIVE",
                makeEsArgs(adbProfile.esList," ")
            ).joinToString(" ")
            Ok(command)
        } ?: Err(AdbCommandBuilderError.MissingAppPackageName)
        return command
    }

    fun makeEsArgs(esList: List<EsData>, separator: String):String {
        return esList
            .filter { it.hasRunnableContent() }
            .mapNotNull {
                esFormatter.makeEsStringForCommandExecution(it)
            }
            .joinToString(separator)
    }
}
