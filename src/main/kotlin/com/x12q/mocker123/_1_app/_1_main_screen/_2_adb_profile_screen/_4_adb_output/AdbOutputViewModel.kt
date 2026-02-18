package com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen._4_adb_output

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.di.AdbProfileScreenScope
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbOutput
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepo
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AdbProfileScreenScope
class AdbOutputViewModel @Inject constructor(
    val adbProfileRepo: AdbProfileRepo
) {
    private val cr = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val logListFlow: StateFlow<List<AdbOutput>> = adbProfileRepo.profileFlow.map {
        it.logOutput
    }.toStateFlow(cr, emptyList())

    fun clearLog() {
        adbProfileRepo.clearLog()
    }

    companion object {
        fun forPreview(): AdbOutputViewModel {
            return AdbOutputViewModel(AdbProfileRepo.forPreview())
        }
    }
}



