package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.es_formatter

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._3_adb_section.messages.es.EscapeType
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.EsData
import org.apache.commons.text.StringEscapeUtils
import javax.inject.Inject

class EsFormatterImp @Inject constructor() : EsFormatter {
    override fun makeEsStringForCommandExecution(es: EsData): String? {
        return when (es.escapeType) {
            EscapeType.JSON -> es.makeExecutionEsWithJsonEscape()
            EscapeType.PLAIN_TEXT -> es.makeExecutionEsWithJsonEscape()
            EscapeType.XML -> es.makeExecutionEsWithXMLEscape()
        }
    }

    /**
     * Es for presentation purpose
     */
    override fun makeEsStringForPresentation(es: EsData): String {
        val vl = es.value?.let{
            formatStr(it, es.escapeType)
        }
        return "--es ${es.key ?: "{key}"} ${vl ?: "{value}"}"
    }

    private fun formatStr(str:String, escapeType: EscapeType):String{
        return when(escapeType){
            EscapeType.JSON -> "\"${StringEscapeUtils.escapeJson(str)}\""
            EscapeType.PLAIN_TEXT -> "\"$str\""
            EscapeType.XML -> "\"${StringEscapeUtils.escapeXml11(str)}\""
        }
    }

    private fun EsData.makeExecutionEsWithXMLEscape(): String? {
        if (value != null && key != null) {
            val xmlValue = formatStr(value,escapeType)
            return "--es $key $xmlValue"
        } else {
            return null
        }
    }

    /**
     * make es for execution with json escaping
     */
    private fun EsData.makeExecutionEsWithJsonEscape(): String? {
        if (value != null && key != null) {
            val valueJson = formatStr(value,escapeType)
            return "--es $key $valueJson"
        } else {
            return null
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
