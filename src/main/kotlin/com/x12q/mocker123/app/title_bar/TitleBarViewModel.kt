package com.x12q.mocker123.app.title_bar

import androidx.lifecycle.ViewModel
import com.x12q.common_di.di.viewmodel_di.ViewModelFactory
import com.x12q.common_utils.toStateFlow
import com.x12q.mocker123.app.main_screen.SelectedAdbProfileIdProvider
import com.x12q.mocker123.app.title_bar.di.TitleBarSubComponent
import com.x12q.mocker123.service.local_service.adb_profile.AdbProfileRepoContainer
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfile
import com.x12q.mocker123.service.local_service.adb_profile.data_structures.AdbProfileId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
class TitleBarViewModel(
    private val adbProfileRepoContainer: AdbProfileRepoContainer,
    private val selectedAdbProfileIdProvider: SelectedAdbProfileIdProvider,
) : ViewModel() {

    private val cr = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val tabInfoCache = mutableMapOf<AdbProfileId, TabInfo>()

    val profileIdsFlow: StateFlow<List<AdbProfileId>> = adbProfileRepoContainer.profileFlow
        .map { profiles ->
            profiles.map { profile ->
                tabInfoCache.getOrPut(profile.id) {
                    TabInfo(profile.id)
                }
                profile.id
            }
        }
        .toStateFlow(cr, emptyList())

    val selectedTabInfo: StateFlow<TabInfo?> = combine(
        profileIdsFlow,
        selectedAdbProfileIdProvider.selectedProfileId
    ) { _, selectedId ->
        selectedId?.let { tabInfoCache[it] }
    }.toStateFlow(cr, null)

    fun getTabInfo(profileId: AdbProfileId): TabInfo? = tabInfoCache[profileId]

    fun onAddClick() {
        val newProfile = AdbProfile.empty()
        adbProfileRepoContainer.add2(newProfile)
        selectedAdbProfileIdProvider.setSelectedAdbProfileId(newProfile.id)
    }

    fun onCloseTabClick(profileId: AdbProfileId) {
        tabInfoCache.remove(profileId)
        adbProfileRepoContainer.remove2(profileId.uuid)
        selectedAdbProfileIdProvider.setSelectedAdbProfileId(adbProfileRepoContainer.profileFlow.value.lastOrNull()?.id)
    }

    fun onSelect(profileId: AdbProfileId) {
        selectedAdbProfileIdProvider.setSelectedAdbProfileId(profileId)
    }

    inner class TabInfo(val adbProfileId: AdbProfileId) {
        val profileDisplayNameFlow: StateFlow<String?> = adbProfileRepoContainer
            .getProfileFlow(adbProfileId.uuid.toString())
            .filterNotNull()
            .map { profile -> profile.name ?: profile.packageName }
            .toStateFlow(cr, null)

        fun changeProfileName(newName: String?) {
            val currentProfile = adbProfileRepoContainer.profileFlow.value
                .firstOrNull { it.id == adbProfileId } ?: return
            val newProfile = currentProfile.setProfileName(newName)
            adbProfileRepoContainer.add2(newProfile)
        }
    }

    @Inject
    @ContributesBinding(TitleBarSubComponent.Scope::class, multibinding = true)
    @SingleIn(TitleBarSubComponent.Scope::class)
    class TitleBarViewModelFactory(
        private val create: () -> TitleBarViewModel,
    ) : ViewModelFactory {
        override val classKey = TitleBarViewModel::class
        override fun createVM(): TitleBarViewModel = create()
    }
}
