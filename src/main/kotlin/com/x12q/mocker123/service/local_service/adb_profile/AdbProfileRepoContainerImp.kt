package com.x12q.mocker123.service.local_service.adb_profile

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileStore
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileStoreDTO
import com.x12q.mocker123.service.local_service.adb_profile.errors.CannotLoadProfile
import com.x12q.mocker123.service.local_service.adb_profile.errors.CannotSaveAdbProfileStore
import com.x12q.mocker123.service.local_service.adb_profile.errors.CannotUpdateProfile
import com.x12q.mocker123.service.system_service.setting.AppSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import com.x12q.mocker123.app.di.AppComponent
import java.util.UUID
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Inject
@ContributesBinding(AppComponent.Scope::class)
@SingleIn(AppComponent.Scope::class)
class AdbProfileRepoContainerImp(val setting: AppSetting) : AdbProfileRepoContainer {

    private val profileMsFlow: MutableStateFlow<List<AdbProfile>> = MutableStateFlow(emptyList())
    override val profileFlow: StateFlow<List<AdbProfile>> = profileMsFlow

    override suspend fun loadProfiles2(): Result<Unit, CannotLoadProfile> = suspendCoroutine { continuation ->
        val jsonValue: String? = setting.loadStringValue(ADB_PROFILE_STORE_KEY)
        if (jsonValue == null) {
            profileMsFlow.value = emptyList()
            continuation.resume(Ok(Unit))
        } else {
            try {
                val dto = adbJson.decodeFromString<AdbProfileStoreDTO>(jsonValue)
                val store = AdbProfileStore.fromDto(dto)
                profileMsFlow.value = store.adProfileList
                continuation.resume(Ok(Unit))
            } catch (e: SerializationException) {
                profileMsFlow.value = emptyList()
                continuation.resume(Err(CannotLoadProfile(e)))
            }
        }
    }

    override fun add2(adbProfile: AdbProfile): Result<Unit, CannotUpdateProfile> {
        val current = profileMsFlow.value
        val index = current.indexOfFirst { it.id == adbProfile.id }
        profileMsFlow.value = if (index >= 0) {
            current.toMutableList().apply { set(index, adbProfile) }
        } else {
            current + adbProfile
        }
        return Ok(Unit)
    }

    override fun getProfileFlow(profileId: String): Flow<AdbProfile?> {
        return profileMsFlow.map { profiles -> profiles.firstOrNull { it.id.uuid.toString() == profileId } }
    }

    override fun remove2(profileId: UUID) {
        profileMsFlow.value = profileMsFlow.value.filter { it.id.uuid != profileId }
    }

    override fun save(): Result<Unit, CannotSaveAdbProfileStore> {
        val profiles = profileMsFlow.value
        if (profiles.isEmpty()) {
            setting.deleteStringValue(ADB_PROFILE_STORE_KEY)
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
