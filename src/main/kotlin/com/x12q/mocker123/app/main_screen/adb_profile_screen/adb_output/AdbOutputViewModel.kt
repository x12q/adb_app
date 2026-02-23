package com.x12q.mocker123.app.main_screen.adb_profile_screen.adb_output

import com.x12q.mocker123.service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbOutput
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileId
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.UUID

@Inject
class AdbOutputViewModel(
    @Assisted val adbProfileId: AdbProfileId,
    val container: AdbProfileRepoContainer,
) {
    private val cr = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val logListFlow: StateFlow<List<AdbOutput>> = container.getProfileFlow(adbProfileId.uuid.toString())
        .filterNotNull()
        .map { it.logOutput }
        .toStateFlow(cr, emptyList())

    fun clearLog() {
        val currentProfile = container.profileFlow.value.firstOrNull { it.id == adbProfileId } ?: return
        val newProfile = currentProfile.clearLog()
        container.add(newProfile)
    }

    companion object {
        fun forPreview(): AdbOutputViewModel {
            return AdbOutputViewModel(
                adbProfileId = AdbProfileId(UUID.randomUUID()),
                container = AdbProfileRepoContainer.forPreview(),
            )
        }
    }
}
