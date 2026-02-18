package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder.AdbAnnotatedCommandBuilderImp
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder.AdbStrCommandBuilderImp
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.es_formatter.EsFormatterImp
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import java.nio.file.Path


interface AdbCommandBuilder {

    fun build(
        adbProfile: AdbProfile,
        adbPath: Path,
        appPackageNamePlaceholder: String,
    ): AdbCommandBuildOutput

    companion object{
        fun forPreview(): AdbCommandBuilder{
            return AdbCommandBuilderImp(
                strCommandBuilder = AdbStrCommandBuilderImp(
                    esFormatter = EsFormatterImp()
                ),
                annotatedCommandBuilder = AdbAnnotatedCommandBuilderImp(
                    esFormatter = EsFormatterImp()
                ),
            )
        }
    }
}
