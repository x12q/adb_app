package com.x12q.mocker123._2_service.local_service.adb_profile.repo

import com.github.michaelbull.result.Result
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbOutput
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotUpdateProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.EsData
import kotlinx.coroutines.flow.Flow

/**
 * This is a fixed point that can provide [AdbProfile], and provide functions to update such profile
 */
interface AdbProfileRepo {
    /**
     * A hot flow of profile managed by this mutator
     */
    val profileFlow: Flow<AdbProfile>

    fun updateProfile(adbProfile: AdbProfile):Result<Unit, CannotUpdateProfile>

    /**
     * this returns the most updated profile manged by this mutator
     */
    fun getProfile(): AdbProfile

    fun setPackageName(packageName:String?): Result<Unit, CannotUpdateProfile>
    fun setProfileName(profileName: String?)
    fun addEs(esData: EsData):Result<Unit, CannotUpdateProfile>
    fun removeEs(esData: EsData):Result<Unit, CannotUpdateProfile>
    fun updateEs(esData: EsData):Result<Unit, CannotUpdateProfile>


    fun addLog(log: AdbOutput)
    fun clearLog()


    companion object{
        fun forPreview(): AdbProfileRepo{
            return AdbProfileRepoImp(AdbProfile.empty())
        }
    }
}
