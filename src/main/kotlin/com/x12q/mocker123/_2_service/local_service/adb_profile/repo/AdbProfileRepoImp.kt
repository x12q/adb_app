package com.x12q.mocker123._2_service.local_service.adb_profile.repo

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbOutput
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotUpdateProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.EsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AdbProfileRepoImp(
    initProfile: AdbProfile
) : AdbProfileRepo {

    private val msFlow = MutableStateFlow(initProfile)
    override val profileFlow: Flow<AdbProfile> = msFlow

    override fun updateProfile(adbProfile: AdbProfile): Result<Unit, CannotUpdateProfile> {
        msFlow.value = adbProfile
        return Ok(Unit)
    }

    override fun getProfile(): AdbProfile {
        return msFlow.value
    }

    override fun setPackageName(packageName: String?): Result<Unit, CannotUpdateProfile> {
        val newProfile = getProfile().copy(packageName = packageName)
        msFlow.value = newProfile
        return Ok(Unit)
    }

    override fun setProfileName(profileName: String?) {
        val newProfile = getProfile().copy(name =  profileName)
        msFlow.value = newProfile
    }

    override fun addEs(esData: EsData): Result<Unit, CannotUpdateProfile> {
        val newProfile = getProfile().addEs(esData)
        msFlow.value = newProfile
        return Ok(Unit)
    }

    override fun removeEs(esData: EsData): Result<Unit, CannotUpdateProfile> {
        val newProfile = getProfile().removeEs(esData)
        msFlow.value = newProfile
        return Ok(Unit)
    }

    override fun updateEs(esData: EsData): Result<Unit, CannotUpdateProfile> {
        val newProfile = getProfile().updateEs(esData)
        msFlow.value = newProfile
        return Ok(Unit)
    }

    override fun addLog(log: AdbOutput) {
        val newProfile = getProfile().addLog(log)
        msFlow.value = newProfile
    }

    override fun clearLog() {
        val newProfile = getProfile().clearLog()
        msFlow.value = newProfile
    }
}
