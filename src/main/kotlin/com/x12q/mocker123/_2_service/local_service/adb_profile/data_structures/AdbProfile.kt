package com.x12q.mocker123._2_service.local_service.adb_profile.data_structures

import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi

data class AdbProfileId(val uuid: UUID)


data class AdbProfile(
    /**
     * A fixed and unique id of a profile
     */
    val id: AdbProfileId,
    val name:String?,
    val packageName: String?,
    /**
     * A map of id -> es data
     */
    val esMap:Map<String, EsData>,
    /**
     * A map of id -> ei data
     */
    val eiMap:Map<String, EiData>,
    val dataEntryOrder:List<String>,
    val logOutput:List<AdbOutput>,
){

    val esList:List<EsData> get() = esMap.values.toList()
    val eiList:List<EiData> get() = eiMap.values.toList()

    fun addLog(log: AdbOutput): AdbProfile{
        return this.copy(logOutput = logOutput + log)
    }

    fun clearLog(): AdbProfile{
        return this.copy(logOutput = emptyList())
    }

    fun addEs(esData: EsData): AdbProfile{
        return this.copy(
            esMap = esMap + (esData.id to esData),
            dataEntryOrder = dataEntryOrder + esData.id
        )
    }

    fun removeEs(esData: EsData): AdbProfile{
        return this.copy(
            esMap = esMap - esData.id,
            dataEntryOrder = dataEntryOrder - esData.id
        )
    }

    fun updateEs(newEs: EsData): AdbProfile{
        return this.copy(
            esMap = esMap + (newEs.id to newEs),
            dataEntryOrder = if(newEs.id in dataEntryOrder){
                dataEntryOrder
            }else{
                dataEntryOrder + newEs.id
            }
        )
    }

    fun addEi(eiData: EiData): AdbProfile{
        return this.copy(
            eiMap = eiMap + (eiData.id to eiData),
            dataEntryOrder = dataEntryOrder + eiData.id
        )
    }

    fun removeEi(eiData: EiData): AdbProfile{
        return this.copy(
            eiMap = eiMap - eiData.id,
            dataEntryOrder = dataEntryOrder - eiData.id
        )
    }

    fun updateEi(newEi: EiData): AdbProfile{
        return this.copy(
            eiMap = eiMap + (newEi.id to newEi),
            dataEntryOrder = if(newEi.id in dataEntryOrder){
                dataEntryOrder
            }else{
                dataEntryOrder + newEi.id
            }
        )
    }

    fun toDto(): AdbProfileDTO {
        return AdbProfileDTO(
            id = AdbProfileIdDTO.fromModel(id),
            name = name,
            packageName = packageName,
            esList = esMap.values.map { EsDataDTO.fromModel(it) },
            eiList = eiMap.values.map { EiDataDTO.fromModel(it) },
            dataEntryOrder = dataEntryOrder,
        )
    }

    companion object{

        fun empty(): AdbProfile{
            return AdbProfile(
                id= AdbProfileId(UUID.randomUUID()),
                name = null,
                packageName = null,
                esMap = emptyMap(),
                eiMap = emptyMap(),
                dataEntryOrder = emptyList(),
                logOutput = emptyList(),
            )
        }

        fun fromDto(dto: AdbProfileDTO): AdbProfile{
            return AdbProfile(
                id = dto.id.toModel(),
                name = dto.name,
                packageName = dto.packageName,
                esMap = dto.esList.associate { esDto ->
                    esDto.toModel().let {
                        it.id to it
                    }
                },
                eiMap = dto.eiList.associate { eiDto ->
                    eiDto.toModel().let {
                        it.id to it
                    }
                },
                dataEntryOrder = dto.dataEntryOrder,
                logOutput = emptyList(),
            )
        }
    }
}


