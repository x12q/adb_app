package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.es_formatter

import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.EsData

interface EsFormatter {
    fun makeEsStringForCommandExecution(es: EsData): String?
    fun makeEsStringForPresentation(es: EsData): String
}

