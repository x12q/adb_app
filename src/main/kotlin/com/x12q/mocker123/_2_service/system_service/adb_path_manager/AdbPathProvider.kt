package com.x12q.mocker123._2_service.system_service.adb_path_manager

import kotlinx.coroutines.flow.Flow
import java.nio.file.Path

/**
 * Contain and provide the path to adb
 */
interface AdbPathProvider {

    /**
     * By default, adb is assumed to be already added to the env variable $PATH.
     * So by default, this function returns "adb".
     */
    fun getCurrentAdbPath(): Path
    /**
     * By default, adb is assumed to be already added to the env variable $PATH.
     * So by default, this flow returns "adb".
     */
    val adbPathFlow: Flow<Path>
    val customAdbPathFlow: Flow<Path?>

    fun setCustomAdbPath(path: Path)

    /**
     * After removing custom adb path, [getCurrentAdbPath] and [adbPathFlow] will return the default path (just "adb").
     */
    fun removeCustomAdbPath()


    companion object{
        fun forPreview(): AdbPathProvider{
            return AdbPathProviderImp()
        }
    }
}



