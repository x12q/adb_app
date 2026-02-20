package com.x12q.mocker123._1_app._1_main_screen

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreenViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreenViewModelFactory
import com.x12q.mocker123._1_app._1_main_screen.di.MainScreenScope
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
import javax.inject.Inject


@MainScreenScope
class MainScreenViewModel @Inject constructor(
    val adbProfileRepoContainer: AdbProfileRepoContainer,
    private val adbProfileScreenViewModelFactory: AdbProfileScreenViewModelFactory,
) {

    private val cr = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val viewModelCache = mutableMapOf<AdbProfileId, AdbProfileScreenViewModel>()

    val profileViewModelsFlow: StateFlow<List<AdbProfileScreenViewModel>> = adbProfileRepoContainer.profileFlow
        .map { profiles ->
            profiles.map { profile ->
                viewModelCache.getOrPut(profile.id) {
                    adbProfileScreenViewModelFactory.create(profile.id)
                }
            }
        }
        .toStateFlow(cr, emptyList())


    private val selectedIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    val selectedViewModel: StateFlow<AdbProfileScreenViewModel?> = combine(
        profileViewModelsFlow,
        selectedIndex
    ) { viewmodelList, selectedIndex ->
        val selectedVm = viewmodelList.getOrNull(selectedIndex)
        selectedVm
    }.toStateFlow(cr,null)

    fun onAddClick() {
        adbProfileRepoContainer.add2(AdbProfile.empty())
        val newIndex = adbProfileRepoContainer.profileFlow.value.lastIndex
        selectedIndex.value = newIndex.coerceAtLeast(0)
    }

    fun onCloseTabClick(profileId: AdbProfileId) {
        viewModelCache.remove(profileId)
        adbProfileRepoContainer.remove2(profileId.uuid)
        val indices = adbProfileRepoContainer.profileFlow.value.indices
        val nextIndex = (selectedIndex.value.takeIf { it in indices } ?: adbProfileRepoContainer.profileFlow.value.lastIndex).coerceAtLeast(0)
        selectedIndex.value = nextIndex
    }

    fun onSelect(viewModel: AdbProfileScreenViewModel) {
        selectedIndex.value = profileViewModelsFlow.value.indexOf(viewModel).takeIf { it != -1 } ?: 0
    }
}
