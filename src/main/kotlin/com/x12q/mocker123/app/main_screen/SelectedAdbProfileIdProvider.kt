package com.x12q.mocker123.app.main_screen

import com.x12q.mocker123.app.main_screen.di.MainScreenSubComponent
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

interface SelectedAdbProfileIdProvider {
    val selectedProfileId: Flow<AdbProfileId?>
    fun setSelectedAdbProfileId(id: AdbProfileId?)
}

@Inject
@SingleIn(MainScreenSubComponent.Scope::class)
@ContributesBinding(MainScreenSubComponent.Scope::class)
class SelectedAdbProfileIdProviderImpl : SelectedAdbProfileIdProvider {
    private val _selectedProfileId: MutableStateFlow<AdbProfileId?> = MutableStateFlow(null)
    override val selectedProfileId: Flow<AdbProfileId?> = _selectedProfileId
    override fun setSelectedAdbProfileId(id: AdbProfileId?) {
        _selectedProfileId.value = id
    }
}
