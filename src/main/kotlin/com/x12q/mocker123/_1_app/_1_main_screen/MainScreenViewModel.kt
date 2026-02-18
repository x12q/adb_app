package com.x12q.mocker123._1_app._1_main_screen

import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreenViewModel
import com.x12q.mocker123._1_app._1_main_screen._2_adb_profile_screen.AdbProfileScreenViewModelFactory
import com.x12q.mocker123._1_app._1_main_screen.di.MainScreenScope
import com.x12q.mocker123._2_service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123._2_service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123._2_service.local_service.adb_profile.repo.AdbProfileRepo
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

    /**
     * convert [AdbProfileRepo] to [AdbProfileScreenViewModel]
     */
    val profileViewModelsFlow: StateFlow<List<AdbProfileScreenViewModel>> = adbProfileRepoContainer.profileReposFlow
        .map { repos ->
            repos?.map { repo ->
                adbProfileScreenViewModelFactory.create(repo)
            } ?: emptyList()
        }
        .toStateFlow(cr,emptyList())


    private val selectedIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    val selectedViewModel: StateFlow<AdbProfileScreenViewModel?> = combine(
        profileViewModelsFlow,
        selectedIndex
    ) { viewmodelList, selectedIndex ->
        val selectedVm = viewmodelList.getOrNull(selectedIndex)
        selectedVm
    }.toStateFlow(cr,null)

    fun onAddClick() {
        adbProfileRepoContainer.add(AdbProfile.empty())
        val newIndex = adbProfileRepoContainer.profileReposFlow.value?.lastIndex
        selectedIndex.value = newIndex ?: 0
    }

    fun onCloseTabClick(repo: AdbProfileRepo) {
        adbProfileRepoContainer.remove(repo)
        // compute the next index to be selected after removal
        adbProfileRepoContainer.profileReposFlow.value?.indices?.also { indices ->
            val nextIndex = ((selectedIndex.value).takeIf { it in indices } ?: adbProfileRepoContainer.profileReposFlow.value?.lastIndex) ?: 0
            selectedIndex.value = nextIndex
        }
    }

    fun onSelect(viewModel: AdbProfileScreenViewModel) {
        selectedIndex.value = profileViewModelsFlow.value.indexOf(viewModel).takeIf { it != -1 } ?: 0
    }
}
