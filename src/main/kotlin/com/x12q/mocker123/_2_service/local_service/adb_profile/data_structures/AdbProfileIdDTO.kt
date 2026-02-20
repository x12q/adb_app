package com.x12q.mocker123._2_service.local_service.adb_profile.data_structures

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AdbProfileIdDTO(
    val uuid:String
) {
    fun toModel(): AdbProfileId {
        return AdbProfileId(UUID.fromString(uuid))
    }

    companion object {
        fun fromModel(model: AdbProfileId): AdbProfileIdDTO {
            return AdbProfileIdDTO(uuid = model.uuid.toString())
        }
    }
}