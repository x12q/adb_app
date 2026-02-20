package com.x12q.mocker123._2_service.local_service.adb_profile

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfileStore
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfileStoreDTO
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotLoadProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotSaveAdbProfileStore
import com.x12q.mocker123._2_service.local_service.adb_profile.errors.CannotUpdateProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepo
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepoImp
import com.x12q.mocker123._2_service.system_service.setting.AppSetting
import com.x12q.common_ui.utils.DataLoadingStatus
import com.x12q.common_ui.utils.LoadedData
import com.x12q.common_ui.utils.LoadError
import com.x12q.common_ui.utils.Loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AdbProfileRepoContainerImp @Inject constructor(val setting: AppSetting) : AdbProfileRepoContainer {

    private val adbProfileReposMsFlow: MutableStateFlow<List<AdbProfileRepo>?> = MutableStateFlow(null)
    private val profileMsFlow: MutableStateFlow<List<AdbProfile>> = MutableStateFlow(emptyList())
    override val profileReposFlow: StateFlow<List<AdbProfileRepo>?> = adbProfileReposMsFlow
    override val profileFlow: StateFlow<List<AdbProfile>> = profileMsFlow

    private val adbProfiles: List<AdbProfile> = profileMsFlow.value

    override fun loadProfiles(): Flow<DataLoadingStatus<Unit, CannotLoadProfile>> {
        return flow {
            emit(Loading)
            setting.loadStringValue(ADB_PROFILE_STORE_KEY).let { jsonValue: String? ->
                if (jsonValue == null) {
                    adbProfileReposMsFlow.value = emptyList()
                    emit(LoadedData(Unit))
                } else {
                    try {
                        val dto = adbJson.decodeFromString<AdbProfileStoreDTO>(jsonValue)
                        val store = AdbProfileStore.fromDto(dto)
                        adbProfileReposMsFlow.value = store.adProfileList.map { profile ->
                            makeAdbProfileMutator(profile)
                        }

                        emit(LoadedData(Unit))

                    } catch (e: SerializationException) {
                        adbProfileReposMsFlow.value = emptyList()
                        emit(LoadError(CannotLoadProfile(e)))
                    }
                }
            }
        }
    }

    override suspend fun loadProfiles2(): Result<Unit, CannotLoadProfile> = suspendCoroutine { continuation ->
        val jsonValue: String? = setting.loadStringValue(ADB_PROFILE_STORE_KEY)
        if (jsonValue == null) {
            adbProfileReposMsFlow.value = emptyList()
            continuation.resume(Ok(Unit))
        } else {
            try {
                val dto = adbJson.decodeFromString<AdbProfileStoreDTO>(jsonValue)
                val store = AdbProfileStore.fromDto(dto)
                adbProfileReposMsFlow.value = store.adProfileList.map { profile ->
                    makeAdbProfileMutator(profile)
                }
                continuation.resume(Ok(Unit))
            } catch (e: SerializationException) {
                adbProfileReposMsFlow.value = emptyList()
                continuation.resume(Err(CannotLoadProfile(e)))
            }
        }
    }

    private fun makeAdbProfileMutator(profile: AdbProfile): AdbProfileRepo {
        return AdbProfileRepoImp(profile)
    }

    override fun add(adbProfile: AdbProfile): Result<AdbProfileRepo, CannotUpdateProfile> {
        val currentProfiles = adbProfileReposMsFlow.value
        if (currentProfiles == null) {
            return Err(CannotUpdateProfile.ContainerInIllegalState)
        } else {
            val newMutator = makeAdbProfileMutator(adbProfile)
            adbProfileReposMsFlow.value = currentProfiles + newMutator
            return Ok(newMutator)
        }
    }

    override fun remove(repo: AdbProfileRepo) {
        val currentRepos = adbProfileReposMsFlow.value
        if (currentRepos != null) {
            val newRepoList = currentRepos - repo
            adbProfileReposMsFlow.value = newRepoList
        }
    }

    override fun add2(adbProfile: AdbProfile): Result<Unit, CannotUpdateProfile> {
        profileMsFlow.value += adbProfile
        return Ok(Unit)
    }

    override fun getProfileFlow(profileId: String): Flow<AdbProfile?> {
        return profileMsFlow.map { profiles -> profiles.firstOrNull { it.id.toString() == profileId } }
    }

    override fun remove2(profileId: UUID) {
        profileMsFlow.value = profileMsFlow.value.filter { it.id != profileId }
    }

    override fun save(): Result<Unit, CannotSaveAdbProfileStore> {
        val profiles = adbProfiles
        if (profiles.isEmpty()) {
            setting.deleteStringValue(ADB_PROFILE_STORE_KEY,)
             return Ok(Unit)
        }
        val store = AdbProfileStore(profiles)
        try {
            val jsonStr = adbJson.encodeToString(store.toDto())
            setting.saveStringValue(ADB_PROFILE_STORE_KEY, jsonStr)
            return Ok(Unit)
        } catch (e: SerializationException) {
            return Err(CannotSaveAdbProfileStore.SerializationError(e))
        }
    }

    companion object {
        private val adbJson = Json {
            ignoreUnknownKeys = true
        }
        private const val ADB_PROFILE_STORE_KEY = "ADB_PROFILE_KEY"
    }
}
