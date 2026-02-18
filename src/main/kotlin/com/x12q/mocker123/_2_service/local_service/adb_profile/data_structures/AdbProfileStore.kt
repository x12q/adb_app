package com.x12q.mocker123._2_service.local_service.adb_profile.data_structures

data class AdbProfileStore(
    val adProfileList: List<AdbProfile>
){

    fun toDto(): AdbProfileStoreDTO{
        return AdbProfileStoreDTO(
            adProfileList = adProfileList.map { it.toDto() }
        )
    }

    companion object{

        fun empty(): AdbProfileStore{
            return AdbProfileStore(
                adProfileList = emptyList()
            )
        }

        fun fromDto(dto: AdbProfileStoreDTO): AdbProfileStore{
            return AdbProfileStore(
                adProfileList = dto.adProfileList.map { profileDto->
                    AdbProfile.Companion.fromDto(profileDto)
                }
            )
        }
    }
}
