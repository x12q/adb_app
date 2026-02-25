package com.x12q.mocker123.service.local_service.adb_profile.data_structures

import com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_message_builder_section.messages.es.EscapeType
import kotlinx.serialization.Serializable

@Serializable
data class EsDataDTO(
    val key: String?,
    val value: String?,
    val escapeType: EscapeType,
    val keyIsLocked: Boolean,
) {
    fun toModel(id: String? = null): EsData {
        val actualId = id ?: java.util.UUID.randomUUID().toString()
        return EsData(
            id = actualId,
            key = key,
            value = value,
            escapeType = escapeType,
            keyIsLocked = keyIsLocked,
        )
    }

    companion object {
        fun fromModel(esData: EsData): EsDataDTO {
            return EsDataDTO(
                key = esData.key,
                value = esData.value,
                escapeType = esData.escapeType,
                keyIsLocked = esData.keyIsLocked,
            )
        }
    }
}
