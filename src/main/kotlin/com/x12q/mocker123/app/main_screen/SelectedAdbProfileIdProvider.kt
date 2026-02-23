package com.x12q.mocker123.app.main_screen

import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileId
import kotlinx.coroutines.flow.Flow

interface SelectedAdbProfileIdProvider {
    val selectedProfileId: Flow<AdbProfileId?>
    fun setSelectedAdbProfileId(id: AdbProfileId?)
}

