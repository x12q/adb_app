package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._4_adb_output

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di.AdbProfileScreenScope
import com.x12q.mocker123._2_service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbOutput
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfileId
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

@AdbProfileScreenScope
class AdbOutputViewModel @Inject constructor(
    val adbProfileId: AdbProfileId,
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
        container.add2(newProfile)
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
