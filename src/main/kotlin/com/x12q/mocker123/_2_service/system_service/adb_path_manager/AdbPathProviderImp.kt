package com.x12q.mocker123._2_service.system_service.adb_path_manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.Path

class AdbPathProviderImp @Inject constructor() : AdbPathProvider {

    override fun getCurrentAdbPath(): Path {
        return getActualAdbPath(customAdPathFlowMsFlow.value)
    }

    private val customAdPathFlowMsFlow: MutableStateFlow<Path?> = MutableStateFlow(null)

    override val customAdbPathFlow: Flow<Path?> = customAdPathFlowMsFlow

    override val adbPathFlow: Flow<Path> = customAdPathFlowMsFlow.map { path ->
        getActualAdbPath(path)
    }

    override fun setCustomAdbPath(path: Path) {
        customAdPathFlowMsFlow.value = path
    }

    override fun removeCustomAdbPath() {
        customAdPathFlowMsFlow.value = null
    }

    private fun getActualAdbPath(currentCustomPath: Path?): Path {
        // by default, assume that adb is in $PATH
        return currentCustomPath ?: Path("adb")
    }

}
