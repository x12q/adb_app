package com.x12q.mocker123.service.local_service.adb_profile.data_structures

import kotlinx.serialization.Serializable

@Serializable
data class AdbProfileStoreDTO(
    val adProfileList: List<AdbProfileDTO>
)
