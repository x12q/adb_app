package com.x12q.mocker123._1_app._1_main_screen

import androidx.lifecycle.ViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreenViewModel
import com.x12q.mocker123._2_service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfileId
import com.x12q.common_utils.toStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import com.x12q.common_di.di.viewmodel_di.ViewModelFactory
import com.x12q.mocker123.di.AppScope
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn


@Inject
class MainScreenViewModel(
    val adbProfileRepoContainer: AdbProfileRepoContainer,
    private val adbProfileScreenViewModelFactory: (AdbProfileId) -> AdbProfileScreenViewModel,
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

    private val selectedProfileId: MutableStateFlow<AdbProfileId?> = MutableStateFlow(null)

    val selectedViewModel: StateFlow<AdbProfileScreenViewModel?> = combine(
        profileIdsFlow,
        selectedProfileId
    ) { _, selectedId ->
        selectedId?.let { viewModelCache[it] }
    }.toStateFlow(cr, null)

    fun getViewModel(profileId: AdbProfileId): AdbProfileScreenViewModel? = viewModelCache[profileId]

    fun onAddClick() {
        val newProfile = AdbProfile.empty()
        adbProfileRepoContainer.add2(newProfile)
        selectedProfileId.value = newProfile.id
    }

    fun onCloseTabClick(profileId: AdbProfileId) {
        viewModelCache.remove(profileId)
        adbProfileRepoContainer.remove2(profileId.uuid)
        // if the closed tab was selected, select the last remaining profile
        if (selectedProfileId.value == profileId) {
            selectedProfileId.value = adbProfileRepoContainer.profileFlow.value.lastOrNull()?.id
        }
    }

    fun onSelect(profileId: AdbProfileId) {
        selectedProfileId.value = profileId
    }

    @Inject
    @ContributesBinding(AppScope::class, multibinding = true)
    @SingleIn(AppScope::class)
    class MainScreenViewModelFactory(
        private val create: () -> MainScreenViewModel,
    ) : ViewModelFactory {
        override val classKey = MainScreenViewModel::class
        override fun createVM(): MainScreenViewModel = create()
    }
}
