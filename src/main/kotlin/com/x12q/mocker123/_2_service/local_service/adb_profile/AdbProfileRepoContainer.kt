package com.x12q.mocker123._2_service.local_service.adb_profile

import com.github.michaelbull.result.Result
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotLoadProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotSaveAdbProfileStore
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotUpdateProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepo
import com.x12q.mocker123._2_service.system_service.setting.AppSetting
import com.x12q.common_ui.utils.DataLoadingStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


interface AdbProfileRepoContainer {


    /**
     * A flow of all current profile in memory
     */
    val profileReposFlow: StateFlow<List<AdbProfileRepo>?>

    /**
     * Load data from persistent layer
     */
    fun loadProfiles(): Flow<DataLoadingStatus<Unit, CannotLoadProfile>>

    fun add(adbProfile: AdbProfile): Result<AdbProfileRepo, CannotUpdateProfile>

    fun remove(repo: AdbProfileRepo)

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
