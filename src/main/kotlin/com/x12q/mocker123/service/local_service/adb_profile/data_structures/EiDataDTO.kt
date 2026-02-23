package com.x12q.mocker123.service.local_service.adb_profile.data_structures

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class EiDataDTO(
    val key: String?,
    val value: Int,
) {
    fun toModel(id: String? = null): EiData {
        return EiData(
            id = id ?: UUID.randomUUID().toString(),
            key = key,
            value = value
        )
    }

    companion object {
        fun fromModel(eiData: EiData): EiDataDTO {
            return EiDataDTO(
                key = eiData.key,
                value = eiData.value
            )
        }
    }
}