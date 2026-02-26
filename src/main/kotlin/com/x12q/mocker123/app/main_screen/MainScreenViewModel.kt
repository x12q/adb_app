package com.x12q.mocker123.app.main_screen

import androidx.lifecycle.ViewModel
import com.x12q.mocker123.app.main_screen.adb_notification_screen.AdbProfileScreenViewModel
import com.x12q.mocker123.app.main_screen.di.MainScreenSubComponent
import com.x12q.mocker123.service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileId
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import com.x12q.common_di.di.viewmodel_di.ViewModelFactory
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn


@Inject
class MainScreenViewModel(
    val adbProfileRepoContainer: AdbProfileRepoContainer,
    private val adbProfileScreenViewModelFactory: (AdbProfileId) -> AdbProfileScreenViewModel,
    private val selectedAdbProfileIdProvider: SelectedAdbProfileIdProvider,
): ViewModel() {

    private val cr = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val viewModelCache = mutableMapOf<AdbProfileId, AdbProfileScreenViewModel>()

    val profileIdsFlow: StateFlow<List<AdbProfileId>> = adbProfileRepoContainer.profileFlow
        .map { profiles ->
            profiles.map { profile ->
                viewModelCache.getOrPut(profile.id) {
                    adbProfileScreenViewModelFactory(profile.id)
                }
                profile.id
            }
        }
        .toStateFlow(cr, emptyList())

    val selectedViewModel: StateFlow<AdbProfileScreenViewModel?> = combine(
        profileIdsFlow,
        selectedAdbProfileIdProvider.selectedProfileId
    ) { _, selectedId ->
        selectedId?.let { viewModelCache[it] }
    }.toStateFlow(cr, null)

    fun getViewModel(profileId: AdbProfileId): AdbProfileScreenViewModel? = viewModelCache[profileId]

    fun onAddClick() {
        val newProfile = AdbProfile.empty()
        adbProfileRepoContainer.add(newProfile)
        selectedAdbProfileIdProvider.setSelectedAdbProfileId(newProfile.id)
    }

    fun onCloseTabClick(profileId: AdbProfileId) {
        viewModelCache.remove(profileId)
        adbProfileRepoContainer.remove(profileId.uuid)
        selectedAdbProfileIdProvider.setSelectedAdbProfileId(adbProfileRepoContainer.profileFlow.value.lastOrNull()?.id)
    }

    fun onSelect(profileId: AdbProfileId) {
        selectedAdbProfileIdProvider.setSelectedAdbProfileId(profileId)
    }

    @Inject
    @ContributesBinding(MainScreenSubComponent.Scope::class, multibinding = true)
    @SingleIn(MainScreenSubComponent.Scope::class)
    class MainScreenViewModelFactory(
        private val create: () -> MainScreenViewModel,
    ) : ViewModelFactory {
        override val classKey = MainScreenViewModel::class
        override fun createVM(): MainScreenViewModel = create()
    }
}
