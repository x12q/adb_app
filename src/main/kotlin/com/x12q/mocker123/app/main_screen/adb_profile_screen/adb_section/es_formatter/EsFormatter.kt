package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_section.es_formatter

import com.x12q.mocker123.service.local_service.adb_profile.data_structures.EsData

interface EsFormatter {
    fun makeEsStringForCommandExecution(es: EsData): String?
    fun makeEsStringForPresentation(es: EsData): String
}

