package com.x12q.mocker123._2_service.local_service.adb_profile

import com.github.michaelbull.result.Result
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotLoadProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotSaveAdbProfileStore
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotUpdateProfile
import com.x12q.mocker123._2_service.system_service.setting.AppSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID


interface AdbProfileRepoContainer {

    val profileFlow: StateFlow<List<AdbProfile>>

    suspend fun loadProfiles2(): Result<Unit, CannotLoadProfile>
    fun getProfileFlow(profileId: String): Flow<AdbProfile?>

    fun add2(adbProfile: AdbProfile): Result<Unit, CannotUpdateProfile>

    fun remove2(profileId: UUID)

    /**
     * save all current profile to persistent layer
     */
    fun save(): Result<Unit, CannotSaveAdbProfileStore>

    companion object {
        fun forPreview(): AdbProfileRepoContainer {
            return AdbProfileRepoContainerImp(AppSetting.forPreview())
        }
    }
}
