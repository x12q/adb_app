package com.x12q.mocker123._2_service.local_service.adb_profile.data_structures

import kotlinx.serialization.Serializable

@Serializable
data class AdbProfileDTO(
    val name:String?,
    val packageName: String?,
    val esList:List<EsDataDTO>,
    val eiList:List<EiDataDTO> = emptyList(),
    val dataEntryOrder:List<String> = emptyList(),
)
