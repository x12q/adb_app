package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.di

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.AdbCommandBuilder
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.AdbCommandBuilderImp
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder.AdbAnnotatedCommandBuilder
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder.AdbAnnotatedCommandBuilderImp
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder.AdbStrCommandBuilder
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.adb_command_builder.sub_builder.AdbStrCommandBuilderImp
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.es_formatter.EsFormatter
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.es_formatter.EsFormatterImp
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di.AdbProfileScreenScope
import dagger.Binds
import dagger.Module

@Module
interface AdbModule {
    @Binds
    @AdbProfileScreenScope
    fun EsFormatter(i: EsFormatterImp): EsFormatter

    @Binds
    @AdbProfileScreenScope
    fun AdbCommandBuilder(i: AdbCommandBuilderImp): AdbCommandBuilder

    @Binds
    @AdbProfileScreenScope
    fun AdbStrCommandBuilder(i: AdbStrCommandBuilderImp): AdbStrCommandBuilder

    @Binds
    @AdbProfileScreenScope
    fun AdbAnnotatedCommandBuilder(i: AdbAnnotatedCommandBuilderImp): AdbAnnotatedCommandBuilder

}
